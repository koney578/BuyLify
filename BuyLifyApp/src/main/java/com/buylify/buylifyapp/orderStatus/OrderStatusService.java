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
}
