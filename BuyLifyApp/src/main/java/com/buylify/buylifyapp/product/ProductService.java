package com.buylify.buylifyapp.product;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.authentication.UserRepository;
import com.buylify.buylifyapp.category.Category;
import com.buylify.buylifyapp.category.CategoryRepository;
import com.buylify.buylifyapp.mappers.ProductMapper;
import com.buylify.buylifyapp.opinion.OpinionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    public static final String IMAGE_PREFIX = "https://firebasestorage.googleapis.com/v0/b/projekt-zespolowy-5362c.appspot.com/o/";
    public static final String MEDIA_PARAM = "?alt=media&token=";


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final OpinionRepository opinionRepository;
    private final ProductMapper mapper;

    public void addProduct(CreateProductDto post, String fileName, Long userId) {
        Product product = mapper.toEntity(post);
        String imageUrl = IMAGE_PREFIX + fileName + MEDIA_PARAM + fileName;

        Category category = categoryRepository.getReferenceById(post.getCategoryId());
        User user = userRepository.getReferenceById(userId);

//       if (post.getAuctionEndsAt() != null) {
//           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
//           LocalDateTime localDateTime = LocalDateTime.parse(post.getAuctionEndsAt(), formatter);
//           product.setAuctionEndsAt(localDateTime);
//       }


        if (post.getAuctionEndsAt() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

            // Parse as an Instant (UTC) and then convert to LocalDateTime
            Instant instant = Instant.parse(post.getAuctionEndsAt());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

            product.setAuctionEndsAt(localDateTime);
        }



        product.setPhoto(imageUrl);
        product.setCategory(category);
        product.setActive(true);
        product.setUser(user);
        productRepository.save(product);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> {
                    ProductDto productDto = mapper.toProductDto(product);

                    Float averageUserStars = opinionRepository.getUserAverageStars(product.getUser().getId());
                    productDto.getUser().setAverageStars(averageUserStars);
                    return productDto;
                })
                .toList();
    }

    public ProductDto getProductById(Long id) {
        return mapper.toProductDto(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found")));
    }

    public List<ProductDto> getProductsByLoggedUser(Long userId) {
        return productRepository.findProductsByUserId(userId)
                .stream()
                .map(product -> {
                    ProductDto productDto = mapper.toProductDto(product);

                    Float averageUserStars = opinionRepository.getUserAverageStars(product.getUser().getId());
                    productDto.getUser().setAverageStars(averageUserStars);
                    return productDto;
                })
                .toList();
    }

    public void updateProduct(Long id, CreateProductDto post, Long userId) {
        Product product = productRepository.findById(id).orElseThrow();
        if (!product.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not allowed");
        }

        if (post.getAuctionEndsAt() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            LocalDateTime localDateTime = LocalDateTime.parse(post.getAuctionEndsAt(), formatter);
            product.setAuctionEndsAt(localDateTime);
        }
        product.setName(post.getName());
        product.setPrice(post.getPrice());
        product.setDescription(post.getDescription());
        product.setCategory(categoryRepository.getReferenceById(post.getCategoryId()));
        product.setCount(post.getCount());
        productRepository.save(product);
    }
}
