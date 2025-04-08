package io.jeidiiy.sirenordersystem.order.service;

import io.jeidiiy.sirenordersystem.order.domain.Order;
import io.jeidiiy.sirenordersystem.order.domain.OrderProduct;
import io.jeidiiy.sirenordersystem.order.domain.OrderStatus;
import io.jeidiiy.sirenordersystem.order.domain.dto.*;
import io.jeidiiy.sirenordersystem.order.exception.NonExistOrderProductException;
import io.jeidiiy.sirenordersystem.order.repository.OrderJpaRepository;
import io.jeidiiy.sirenordersystem.product.domain.Product;
import io.jeidiiy.sirenordersystem.product.service.ProductService;
import io.jeidiiy.sirenordersystem.store.domain.Store;
import io.jeidiiy.sirenordersystem.store.service.StoreService;
import io.jeidiiy.sirenordersystem.user.domain.User;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

  private final OrderJpaRepository orderJpaRepository;
  private final OrderProductService orderProductService;
  private final UserService userService;
  private final StoreService storeService;
  private final ProductService productService;

  public List<OrderResponseDto> getOrderResponseDtoByCurrentUser(String currentUsername) {
    User user = userService.getUserByUsername(currentUsername);
    List<Order> orders = orderJpaRepository.findAllByUserId(user.getId());
    List<OrderResponseDto> result = new ArrayList<>();
    for (Order order : orders) {
      List<OrderProduct> orderProducts = orderProductService.findAllByOrderId(order.getId());
      List<OrderProductResponseDto> orderProductResponseDtos =
          orderProducts.stream().map(OrderProductResponseDto::from).toList();
      result.add(OrderResponseDto.from(order, orderProductResponseDtos));
    }

    // 주문일 기준 최신순
    result.sort(Comparator.comparing(OrderResponseDto::orderedDateTime).reversed());

    return result;
  }

  public void createOrder(OrderPostRequestBody requestBody, String currentUsername) {
    if (requestBody.orderProductDtos().isEmpty()) {
      throw new NonExistOrderProductException("주문된 상품이 없습니다");
    }

    User user = userService.getUserByUsername(currentUsername);
    Store store = storeService.findById(requestBody.storeId());
    Order order = Order.of(user, store, OrderStatus.PENDING);
    int totalPrice = 0;

    for (OrderProductDto orderProductDto : requestBody.orderProductDtos()) {
      Product product = productService.findById(orderProductDto.id());
      OrderProduct orderProduct = OrderProduct.of(order, product, orderProductDto.quantity());
      totalPrice += product.getBasePrice() * orderProductDto.quantity();
      orderProductService.save(orderProduct);
    }

    order.setTotalPrice(totalPrice);
    orderJpaRepository.save(order);
  }
}
