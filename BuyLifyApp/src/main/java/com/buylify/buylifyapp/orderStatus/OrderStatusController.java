package com.buylify.buylifyapp.orderStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAuthority('administrator')")
    @PostMapping
    public void addOrderStatus(OrderStatus orderStatus) {
        orderStatusService.addOrderStatus(orderStatus);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @DeleteMapping("/{id}")
    public void deleteOrderStatus(@PathVariable("id") Long id) {
        orderStatusService.deleteOrderStatus(id);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @PutMapping("/{id}")
    public void editOrderStatus(@PathVariable("id") Long id, OrderStatus orderStatusToEdit) {
        orderStatusService.editOrderStatus(id, orderStatusToEdit);
    }
}
