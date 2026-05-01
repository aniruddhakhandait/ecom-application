package com.app.ecom_application.service;


import com.app.ecom_application.dto.OrderItemDTO;
import com.app.ecom_application.dto.OrderResponse;
import com.app.ecom_application.model.*;
import com.app.ecom_application.repository.OrderRepository;
import com.app.ecom_application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

   // creating order
    public Optional <OrderResponse> createOrder(String userId) {
     // validate for cart item
        List<CartItem> cartItem = cartService.getCart(userId);
        if (cartItem.isEmpty()){
          return Optional.empty();
        }

        // validate for user

      Optional <User> userOptional = userRepository.findById(Long.valueOf(userId));
       if (userOptional.isEmpty()){
          return Optional.empty();
         }
      User user = userOptional.get();


        // calculate total price
        BigDecimal totalPrice = cartItem.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO , BigDecimal:: add);

        // create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List <OrderItem> orderItems = cartItem.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProduct(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                )).toList();

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // clear the cart
        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
         return new OrderResponse(
                 order.getId(),
                 order.getTotalAmount(),
                 order.getStatus(),
                 order.getItems().stream()
                         .map(orderItem -> new OrderItemDTO(
                              orderItem.getId(),
                               orderItem.getProduct().getId(),
                                 orderItem.getQuantity(),
                                 orderItem.getPrice(),
                                 orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))

                         ))
                         .toList(),
                 order.getCreatedAt()
         );

    }
}
