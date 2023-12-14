package com.buylify.buylifyapp.authentication;

import com.buylify.buylifyapp.followedProduct.FollowedProduct;
import com.buylify.buylifyapp.followedProduct.FollowedProductService;
import com.buylify.buylifyapp.product.ProductDto;
import com.buylify.buylifyapp.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping
    public UserDto updateUser(@RequestBody @Validated EditUserDto editUserDto, Authentication authentication) {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        return userService.updateUser(userId, editUserDto);
    }

    @PatchMapping("/type/premium")
    public void setPremiumUserType(Authentication authentication) {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        userService.setPremiumUserType(userId);
    }
    @PatchMapping("/type/basic")
    public void setBasicUserType(Authentication authentication) {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        userService.setBasicUserType(userId);
    }
}
