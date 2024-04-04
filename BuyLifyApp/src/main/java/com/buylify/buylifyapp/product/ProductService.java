package com.buylify.buylifyapp.product;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.authentication.UserProductDto;
import com.buylify.buylifyapp.authentication.UserRepository;
import com.buylify.buylifyapp.category.Category;
import com.buylify.buylifyapp.category.CategoryRepository;
import com.buylify.buylifyapp.mappers.ProductMapper;
import com.buylify.buylifyapp.opinion.OpinionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @PersistenceContext
    private EntityManager entityManager;

    public void addProduct(CreateProductDto post, String fileName, Long userId) {
        Product product = mapper.toEntity(post);
        String imageUrl = IMAGE_PREFIX + fileName + MEDIA_PARAM + fileName;

        Category category = categoryRepository.getReferenceById(post.getCategoryId());
        User user = userRepository.getReferenceById(userId);

        product.setPhoto(imageUrl);
        product.setCategory(category);
        product.setActive(true);
        product.setUser(user);
        productRepository.save(product);
    }

    public List<ProductDto> getAllProducts(Long categoryId, Float minPrice, Float maxPrice) {
        return productRepository.findProductsFiltered(categoryId, minPrice, maxPrice, 0)
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


        product.setName(post.getName());
        product.setPrice(post.getPrice());
        product.setDescription(post.getDescription());
        product.setCategory(categoryRepository.getReferenceById(post.getCategoryId()));
        product.setCount(post.getCount());
        productRepository.save(product);
    }

    public void deactivateProduct(Long id, Long userId) {
        Product product = productRepository.findById(id).orElseThrow();
        if (product.getUser().getId().equals(userId)){
            product.setActive(false);
            productRepository.save(product);
        }

    }

    public List<ProductOrderDto> getPurchasedProducts(Long userId) {
        String sql = "SELECT p.*, op.id_order AS orderId " +
                        "FROM products p " +
                        "JOIN orders_products op ON p.id = op.id_product " +
                        "JOIN orders o ON op.id_order = o.id " +
                        "WHERE o.id_user = :userId";

        List<Object[]> resultList = entityManager.createNativeQuery(sql)
                .setParameter("userId", userId)
                .getResultList();

        List<ProductOrderDto> productOrders = new ArrayList<>();
        for (Object[] result : resultList) {
            ProductDto productDto = new ProductDto();
            productDto.setId(((Number) result[0]).longValue());
            productDto.setCount((Integer) result[1]);

            Timestamp timestamp = (Timestamp) result[2];
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            productDto.setCreatedAt(localDateTime);

            productDto.setDescription((String) result[3]);
            productDto.setName((String) result[6]);
            productDto.setPhoto((String) result[7]);
            productDto.setPrice((Float) result[8]);

            Long categoryId = ((Number) result[10]).longValue();
            Category category = categoryRepository.findById(categoryId).orElseThrow();
            productDto.setCategory(category);

            Long idUser = ((Number) result[11]).longValue();
            User user = userRepository.findById(idUser).orElseThrow();
            UserProductDto userProductDto = new UserProductDto();
            userProductDto.setId(user.getId());
            productDto.setUser(userProductDto);

            Long orderId = ((Number) result[12]).longValue();

            ProductOrderDto productOrderDto = new ProductOrderDto();
            productOrderDto.setProduct(productDto);
            productOrderDto.setOrderId(orderId);

            productOrders.add(productOrderDto);
        }


        return productOrders;

    }
}
















