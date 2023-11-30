package com.buylify.buylifyapp.orderStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order-statuses")
@RequiredArgsConstructor
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @GetMapping
    public List<OrderStatus> getAllOrderStatuses() {
        return orderStatusService.getAllOrderStatuses();
    }
}
