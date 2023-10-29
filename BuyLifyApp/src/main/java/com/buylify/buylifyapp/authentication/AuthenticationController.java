package com.buylify.buylifyapp.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody @Validated UserDto userDto){
        userService.registerUser(userDto);
    }

}
