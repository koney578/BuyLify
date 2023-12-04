package com.buylify.buylifyapp.order;

import com.buylify.buylifyapp.address.Address;
import com.buylify.buylifyapp.address.AddressRepository;
import com.buylify.buylifyapp.mappers.OrderMapper;
import com.buylify.buylifyapp.orderStatus.OrderStatus;
import com.buylify.buylifyapp.orderStatus.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final AddressRepository addressRepository;
    private final OrderMapper orderMapper;

    public void addOrder(CreateOrderDto createOrderDto, Long userId) {
        Address address = addressRepository.save(createOrderDto.getAddress());
        Order order = orderMapper.toEntity(createOrderDto);
        order.setIdAddress(address.getId());
        order.setIdUser(userId);
        //TODO: NIECH KTOS TO WYCIAGNIE Z BAZY DANYCH, A NIE NA SZTYWNO
        order.setIdOrderStatus(1l);
        orderRepository.save(order);
    }



    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
