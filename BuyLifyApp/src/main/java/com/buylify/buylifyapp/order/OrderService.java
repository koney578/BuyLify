package com.buylify.buylifyapp.order;

import com.buylify.buylifyapp.address.Address;
import com.buylify.buylifyapp.address.AddressRepository;
import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.authentication.UserRepository;
import com.buylify.buylifyapp.deliveryMethod.DeliveryMethod;
import com.buylify.buylifyapp.deliveryMethod.DeliveryMethodRepository;
import com.buylify.buylifyapp.mappers.OrderMapper;
import com.buylify.buylifyapp.orderStatus.OrderStatus;
import com.buylify.buylifyapp.orderStatus.OrderStatusRepository;
import com.buylify.buylifyapp.paymentMethod.PaymentMethod;
import com.buylify.buylifyapp.paymentMethod.PaymentMethodRepository;
import com.buylify.buylifyapp.product.Product;
import com.buylify.buylifyapp.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    public void addOrder(CreateOrderDto createOrderDto, Long userId) {
        Product product = productRepository.findById(createOrderDto.getIdProduct()).orElseThrow();
        if (product.getCount() < createOrderDto.getProductQuantity()) {
            throw new RuntimeException("Not enough products");
        }
        User user = userRepository.findById(userId).orElseThrow();
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(createOrderDto.getIdDeliveryMethod()).orElseThrow();
        PaymentMethod paymentMethod = paymentMethodRepository.findById(createOrderDto.getIdPaymentMethod()).orElseThrow();
        Address address = addressRepository.save(createOrderDto.getAddress());
        OrderStatus orderStatus = orderStatusRepository.findById(1l).orElseThrow();
        Order order = orderMapper.toEntity(createOrderDto);
        order.setProduct(product);
        order.setAddress(address);
        order.setUser(user);
        order.setDeliveryMethod(deliveryMethod);
        order.setPaymentMethod(paymentMethod);
        order.setTotalValue(product.getPrice() * createOrderDto.getProductQuantity());
        //TODO: NIECH KTOS TO WYCIAGNIE Z BAZY DANYCH, A NIE NA SZTYWNO
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
    }



    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderDto> getOrdersByLoggedUser(Long userId) {
        return orderRepository.findOrdersByUserId(userId).stream().map(orderMapper::toDto).toList();
    }
}
