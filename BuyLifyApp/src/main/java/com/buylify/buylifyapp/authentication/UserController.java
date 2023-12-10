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
    private final FollowedProductService followedProductService;

    @PutMapping("/update")
    public UserDto updateUser(@RequestBody @Validated EditUserDto editUserDto, Authentication authentication) {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        return userService.updateUser(userId, editUserDto);
    }

    @GetMapping("/{id}/followed-product")
    public List<FollowedProduct> getFollowedProducts(@PathVariable("id") Long id, Authentication authentication) throws Exception {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        if (!id.equals(userId)){
            throw new Exception("Bad request");
        }
        return followedProductService.getAllFollowedProducts(id);
    }
}
