package com.buylify.buylifyapp.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.user.id = :userId " +
            "AND p.isActive = true")
    List<Product> findProductsByUserId(@Param("userId") Long userId);

    List<Product> findProductsByCountGreaterThan(Integer count);

    @Query("SELECT p FROM Product p WHERE (:categoryId IS NULL OR (:categoryId = 0 AND p.category.id = p.category.id) OR p.category.id = :categoryId) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND p.count > :count AND p.isActive = true ")
    List<Product> findProductsFiltered(@Param("categoryId") Long categoryId,
                                       @Param("name") String name,
                                       @Param("minPrice") Float minPrice,
                                       @Param("maxPrice") Float maxPrice,
                                       @Param("count") Integer count);

}
