package com.buylify.buylifyapp.orderStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

}
