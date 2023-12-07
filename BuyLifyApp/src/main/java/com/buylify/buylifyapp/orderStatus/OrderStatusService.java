package com.buylify.buylifyapp.orderStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    public List<OrderStatus> getAllOrderStatuses() {
        return orderStatusRepository.findAll();
    }

    public void addOrderStatus(OrderStatus orderStatus) {
        orderStatusRepository.save(orderStatus);
    }

    public void deleteOrderStatus(Long id) {
        orderStatusRepository.deleteById(id);
    }


    public void editOrderStatus(Long id, OrderStatus orderStatusToEdit) {
        OrderStatus orderStatus = orderStatusRepository.findById(id).orElseThrow();
        orderStatus.setName(orderStatusToEdit.getName());
        orderStatusRepository.save(orderStatus);
    }
}
