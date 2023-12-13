package com.buylify.buylifyapp.product;

import com.buylify.buylifyapp.firebase.FirebaseFileService;
import com.buylify.buylifyapp.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final FirebaseFileService firebaseFileService;

    @PostMapping
    public void addProduct(@RequestPart("post") CreateProductDto post,
                           @RequestPart("file") MultipartFile file,
                           Authentication authentication) throws IOException {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        String fileName = firebaseFileService.saveTest(file);
        productService.addProduct(post, fileName, userId);
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/posted")
    public List<ProductDto> getProductsByLoggedUser(Authentication authentication) {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        return productService.getProductsByLoggedUser(userId);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody CreateProductDto post, Authentication authentication) {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        productService.updateProduct(id, post, userId);
    }
}
