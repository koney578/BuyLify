package com.buylify.buylifyapp.order;

import com.buylify.buylifyapp.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void addOrder(@RequestBody CreateOrderDto body,
                         Authentication authentication){
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        orderService.addOrder(body,userId);

    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }


    @GetMapping
    public List<OrderDto> getOrdersByLoggedUser(Authentication authentication) {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        return orderService.getOrdersByLoggedUser(userId);
    }
}
