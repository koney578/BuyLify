package com.buylify.buylifyapp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.exp}")
    private Long exp;

    public String generate(Authentication authentication) {
        SecurityUser user = (SecurityUser) authentication.getPrincipal();

        byte[] signingKey = secretKey.getBytes();

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(exp).toInstant()))
                .setSubject(user.getUsername())
                .claim("id_user", user.getId())
                .compact();
    }

    public Optional<Jws<Claims>> validate(String token) {
        try {
            byte[] signingKey = secretKey.getBytes();

            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);

            return Optional.of(jws);
        } catch (SignatureException e) {
            log.error("Request to parse JWT with invalid signature: {} failed: {}", token, e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Request to parse unsupported JWT: {} failed: {}", token, e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Request to parse invalid JWT: {} failed: {}", token, e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Request to parse expired JWT: {} failed: {}", token, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Request to parse null or empty JWT: {} failed: {}", token, e.getMessage());
        }
        return Optional.empty();
    }
}
