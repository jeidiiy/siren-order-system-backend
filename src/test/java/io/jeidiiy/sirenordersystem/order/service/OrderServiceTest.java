package io.jeidiiy.sirenordersystem.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeidiiy.sirenordersystem.order.domain.Order;
import io.jeidiiy.sirenordersystem.order.domain.OrderProduct;
import io.jeidiiy.sirenordersystem.order.domain.OrderStatus;
import io.jeidiiy.sirenordersystem.order.domain.dto.*;
import io.jeidiiy.sirenordersystem.order.repository.OrderJpaRepository;
import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.Product;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;
import io.jeidiiy.sirenordersystem.product.domain.dto.ProductDto;
import io.jeidiiy.sirenordersystem.product.domain.food.dto.FoodDto;
import io.jeidiiy.sirenordersystem.product.service.ProductService;
import io.jeidiiy.sirenordersystem.store.domain.Store;
import io.jeidiiy.sirenordersystem.store.service.StoreService;
import io.jeidiiy.sirenordersystem.user.domain.User;
import io.jeidiiy.sirenordersystem.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("[Service] 주문 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
  @InjectMocks OrderService sut;

  @Mock OrderJpaRepository orderJpaRepository;
  @Mock OrderProductService orderProductService;
  @Mock UserService userService;
  @Mock StoreService storeService;
  @Mock ProductService productService;
  @Mock ObjectMapper mapper;

  @DisplayName("정상적인 주문 요청 시 주문을 생성한다.")
  @Test
  void givenValidOrder_whenRequesting_thenCreate() {
    // given
    var currentUsername = "loginUsername";
    int storeId = 1;
    var user = new User();
    ReflectionTestUtils.setField(user, "username", currentUsername);
    var store = new Store();
    ReflectionTestUtils.setField(store, "id", storeId);
    Order order = Order.of(user, store, OrderStatus.PENDING);
    String pickupOption = "to-go";
    int americanoId = 1;
    int americanoQuantity = 2;
    BeverageDto americano =
        (BeverageDto)
            ProductDto.of(
                americanoId, "아메리카노", "Americano", "아메리카노", 4500, "아메리카노 이미지", Category.BEVERAGE);

    int sandwichId = 2;
    int sandwichQuantity = 2;
    FoodDto sandwich =
        (FoodDto)
            ProductDto.of(
                sandwichId, "샌드위치", "sandwich", "샌드위치 설명", 6700, "샌드위치 이미지", Category.FOOD);

    OrderPostRequestBody orderPostRequestBody =
        new OrderPostRequestBody(
            storeId,
            pickupOption,
            List.of(
                new OrderProductDto(americanoId, americanoQuantity),
                new OrderProductDto(sandwichId, sandwichQuantity)));

    int americanoOrderTotalPrice = 4500 * 2;
    int sandwichOrderTotalPrice = 6700 * 2;
    int expectedTotalPrice = americanoOrderTotalPrice + sandwichOrderTotalPrice;

    given(userService.getUserByUsername(currentUsername))
        .willReturn(User.builder().username(currentUsername).build());
    given(storeService.findById(storeId)).willReturn(store);
    given(productService.findById(americanoId)).willReturn(americano.toEntity());
    given(productService.findById(sandwichId)).willReturn(sandwich.toEntity());

    ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
    given(orderJpaRepository.save(any(Order.class)))
        .willAnswer(invocation -> invocation.<Order>getArgument(0));

    // when
    sut.createOrder(orderPostRequestBody, currentUsername);

    // then
    then(orderJpaRepository).should().save(orderCaptor.capture());
    Order savedOrder = orderCaptor.getValue();

    assertThat(expectedTotalPrice).isEqualTo(savedOrder.getTotalPrice());
  }

  @DisplayName("로그인한 사용자가 본인의 주문 내역을 확인한다.")
  @Test
  void givenUsername_whenRequesting_thenResponse() {
    // given
    var currentUsername = "loginUsername";
    var userId = 1;
    User user = User.builder().username(currentUsername).build();
    ReflectionTestUtils.setField(user, "id", userId);
    Store store = Store.of("storeName", null, "imageUrl", null, null, null, false);

    var order1Id = 1;
    Order order1 = Order.of(user, store, OrderStatus.COMPLETE);
    ReflectionTestUtils.setField(order1, "id", order1Id);
    ReflectionTestUtils.setField(order1, "createdAt", LocalDateTime.now());
    OrderProduct orderProduct1 =
        OrderProduct.of(
            order1, Product.of(1, "아메리카노", null, null, null, null, Category.BEVERAGE), 1);
    OrderProduct orderProduct2 =
        OrderProduct.of(order1, Product.of(2, "샌드위치", null, null, null, null, Category.FOOD), 1);
    List<OrderProduct> orderProducts1 = List.of(orderProduct1, orderProduct2);

    var order2Id = 2;
    Order order2 = Order.of(user, store, OrderStatus.COMPLETE);
    ReflectionTestUtils.setField(order2, "id", order2Id);
    ReflectionTestUtils.setField(order2, "createdAt", LocalDateTime.now().plusHours(4));
    OrderProduct orderProduct3 =
            OrderProduct.of(
                    order1, Product.of(1, "아메리카노", null, null, null, null, Category.BEVERAGE), 1);
    OrderProduct orderProduct4 =
            OrderProduct.of(order1, Product.of(2, "샌드위치", null, null, null, null, Category.FOOD), 1);
    OrderProduct orderProduct5 =
            OrderProduct.of(order1, Product.of(3, "머그컵", null, null, null, null, Category.MERCHANDISE), 1);
    List<OrderProduct> orderProducts2 = List.of(orderProduct3, orderProduct4, orderProduct5);

    List<Order> orders = List.of(order1, order2);

    given(userService.getUserByUsername(currentUsername)).willReturn(user);
    given(orderJpaRepository.findAllByUserId(userId)).willReturn(orders);
    given(orderProductService.findAllByOrderId(order1.getId())).willReturn(orderProducts1);
    given(orderProductService.findAllByOrderId(order2.getId())).willReturn(orderProducts2);

    // when
    List<OrderResponseDto> result = sut.getOrderResponseDtoByCurrentUser(currentUsername);

    // then
    assertThat(result.size()).isEqualTo(2);
    assertThat(result.get(0).orderedDateTime()).isAfter(result.get(1).orderedDateTime());
    assertThat(result.get(0).orderProductResponseDtos().size()).isEqualTo(3);
    assertThat(result.get(1).orderProductResponseDtos().size()).isEqualTo(2);
    then(orderJpaRepository).should().findAllByUserId(userId);
  }
}
