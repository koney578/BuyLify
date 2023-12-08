package com.buylify.buylifyapp.authentication;

import com.buylify.buylifyapp.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/update")
    public UserDto updateUser(@RequestBody @Validated EditUserDto editUserDto, Authentication authentication) {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        return userService.updateUser(userId, editUserDto);
    }
}
