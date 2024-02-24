package com.buylify.buylifyapp.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersProductsRepository extends JpaRepository<OrdersProducts, Long> {
}
