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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final OrdersProductsRepository ordersProductsRepository;
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

//    public void addOrder(CreateOrderDto createOrderDto, Long userId) {
//        Product product = productRepository.findById(createOrderDto.getIdProduct()).orElseThrow();
//
//        if (product.getCount() < createOrderDto.getProductQuantity()) {
//            throw new RuntimeException("Not enough products");
//        }
//
//        // Update product count
//        product.setCount(product.getCount() - createOrderDto.getProductQuantity());
//        productRepository.save(product);
//
//        User user = userRepository.findById(userId).orElseThrow();
//        DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(createOrderDto.getIdDeliveryMethod()).orElseThrow();
//        PaymentMethod paymentMethod = paymentMethodRepository.findById(createOrderDto.getIdPaymentMethod()).orElseThrow();
//        Address address = addressRepository.save(createOrderDto.getAddress());
//        OrderStatus orderStatus = orderStatusRepository.findById(1L).orElseThrow();
//        Order order = orderMapper.toEntity(createOrderDto);
//        order.setAddress(address);
//        order.setUser(user);
//        order.setDeliveryMethod(deliveryMethod);
//        order.setPaymentMethod(paymentMethod);
//
//        float totalValue = product.getPrice() * createOrderDto.getProductQuantity();
//
//        order.setTotalValue(totalValue);
//
//        order.setOrderStatus(orderStatus);
//        orderRepository.save(order);
//    }

    public void addNewOrder(CreateOrderDto createOrderDto, Long userId) {

        User user = userRepository.findById(userId).orElseThrow();
        createOrderDto.getOrderInfo().forEach(orderInfo -> {
            Address address = addressRepository.save(orderInfo.getAddress());
            DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(orderInfo.getIdDeliveryMethod()).orElseThrow();
            PaymentMethod paymentMethod = paymentMethodRepository.findById(orderInfo.getIdPaymentMethod()).orElseThrow();
            OrderStatus orderStatus = orderStatusRepository.findById(1L).orElseThrow();
            Order order = new Order();
            order.setAddress(address);
            order.setUser(user);
            order.setTotalValue(1);
            order.setProductQuantity(1);
            order.setDeliveryMethod(deliveryMethod);
            order.setPaymentMethod(paymentMethod);
            //order.setTotalValue(orderInfo.getTotalValue());
            order.setOrderStatus(orderStatus);
            order.setCreateAt(LocalDateTime.now());
            orderRepository.save(order);

            createOrderDto.getProducts().stream().filter(orderProduct -> orderProduct.getSellerId().equals(orderInfo.getSellerId()))
                    .forEach(orderProduct -> {
                Product product = productRepository.findById(orderProduct.getProductId()).orElseThrow();
                if (product.getCount() < orderProduct.getProductQuantity()) {
                    throw new RuntimeException("Not enough products");
                }
                // Update product count
                product.setCount(product.getCount() - orderProduct.getProductQuantity());
                productRepository.save(product);

                OrdersProducts ordersProducts = new OrdersProducts();
                ordersProducts.setProduct(product);
                ordersProducts.setOrder(order);
                ordersProducts.setProductQuantity(orderProduct.getProductQuantity());
                ordersProducts.setSumPrice(product.getPrice() * orderProduct.getProductQuantity());
                ordersProductsRepository.save(ordersProducts);
            });
        });
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderDto> getOrdersByLoggedUser(Long userId) {
        return orderRepository.findOrdersByUserId(userId).stream().map(orderMapper::toDto).toList();
    }
}
