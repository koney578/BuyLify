package com.buylify.buylifyapp.order;

import com.buylify.buylifyapp.address.Address;
import com.buylify.buylifyapp.address.AddressRepository;
import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.authentication.UserRepository;
import com.buylify.buylifyapp.deliveryMethod.DeliveryMethod;
import com.buylify.buylifyapp.deliveryMethod.DeliveryMethodRepository;
import com.buylify.buylifyapp.mappers.OrderMapper;
import com.buylify.buylifyapp.notification.Notification;
import com.buylify.buylifyapp.notification.NotificationRepository;
import com.buylify.buylifyapp.notificationType.NotificationType;
import com.buylify.buylifyapp.notificationType.NotificationTypeRepository;
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
    private final NotificationTypeRepository notificationTypeRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    public void addNewOrder(CreateOrderDto createOrderDto, Long userId) {

        User user = userRepository.findById(userId).orElseThrow();
        Address address = addressRepository.save(createOrderDto.getAddress());
        createOrderDto.getOrderInfo().forEach(orderInfo -> {
            DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(orderInfo.getIdDeliveryMethod()).orElseThrow();
            PaymentMethod paymentMethod = paymentMethodRepository.findById(orderInfo.getIdPaymentMethod()).orElseThrow();
            OrderStatus orderStatus = orderStatusRepository.findById(1L).orElseThrow();
            Order order = new Order();
            order.setAddress(address);
            order.setUser(user);
            order.setDeliveryMethod(deliveryMethod);
            order.setPaymentMethod(paymentMethod);
            order.setOrderStatus(orderStatus);
            order.setCreateAt(LocalDateTime.now());
            order.setTotalValue(0);

            createOrderDto.getProducts().stream().filter(orderProduct -> orderProduct.getSellerId().equals(orderInfo.getSellerId()))
                    .forEach(orderProduct -> {
                Product product = productRepository.findById(orderProduct.getProductId()).orElseThrow();
                if (product.getCount() < orderProduct.getProductQuantity()) {
                    throw new RuntimeException("Not enough products");
                }
                // Update product count
                        if (product.getCount() - orderProduct.getProductQuantity() == 0) {
                            Notification notification = new Notification();
                            notification.setMessage("Product " + product.getName() + " is out of stock");
                            notification.setUser(user);
                            notification.setChecked(false);

                            NotificationType notificationType = notificationTypeRepository.findById(1L).orElseThrow();
                            notification.setNotificationType(notificationType);
                            notificationRepository.save(notification);
                        }
                product.setCount(product.getCount() - orderProduct.getProductQuantity());
                productRepository.save(product);

                OrdersProducts ordersProducts = new OrdersProducts();
                ordersProducts.setProduct(product);
                ordersProducts.setProductQuantity(orderProduct.getProductQuantity());
                order.setTotalValue(order.getTotalValue() + product.getPrice() * orderProduct.getProductQuantity());
                orderRepository.save(order);
                ordersProducts.setOrder(order);
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
