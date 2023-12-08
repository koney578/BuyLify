package com.buylify.buylifyapp.authentication;

import com.buylify.buylifyapp.security.SecurityUser;
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

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody @Validated RegisterDto registerDto){
        userService.registerUser(registerDto);
        var userAndToken = authenticateGetToken(registerDto.getUsername(), registerDto.getPassword());
        SecurityUser securityUser = (SecurityUser) userAndToken.get(0);
        String token = (String) userAndToken.get(1);

        UserDto userDto = userToDto(securityUser);

        return new AuthenticationResponse(token, userDto);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Validated LoginDto loginDto) {
        var userAndToken = authenticateGetToken(loginDto.getUsername(), loginDto.getPassword());
        SecurityUser securityUser = (SecurityUser) userAndToken.get(0);
        String token = (String) userAndToken.get(1);

        UserDto userDto = userToDto(securityUser);

        return new AuthenticationResponse(token, userDto);
    }

    private List<Object> authenticateGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        List<Object> userAndToken = new ArrayList<>();
        userAndToken.add(user);
        System.out.println(user.getAuthorities());
        userAndToken.add(tokenProvider.generate(authentication));
        return userAndToken;
    }

    private UserDto userToDto(SecurityUser user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRoles(user.getAuthorities());
        return userDto;
    }
}
