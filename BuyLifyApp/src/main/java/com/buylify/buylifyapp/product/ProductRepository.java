package com.buylify.buylifyapp.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.user.id = :userId")
    List<Product> findProductsByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR p.category.id = :categoryId)")
    List<Product> findProductsFiltered(@Param("categoryId") Long categoryId);

    List<Product> findProductsByCountGreaterThan(Integer count);

    @Query("SELECT p FROM Product p WHERE (:categoryId IS NULL OR p.category.id = :categoryId) AND p.count > :count AND p.isActive = true")
    List<Product> findProductsFilteredByCountAndCategory(@Param("categoryId") Long categoryId, @Param("count") Integer count);

}
