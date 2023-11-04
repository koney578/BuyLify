package com.buylify.buylifyapp.authentication;

import com.buylify.buylifyapp.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody @Validated UserDto userDto){
        userService.registerUser(userDto);
        String token = authenticateGetToken(userDto.getUsername(), userDto.getPassword());
        return new AuthenticationResponse(token);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Validated LoginDto loginDto) {
        String token = authenticateGetToken(loginDto.getUsername(), loginDto.getPassword());
        return new AuthenticationResponse(token);
    }

    private String authenticateGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }
}
