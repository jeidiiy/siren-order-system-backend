package io.jeidiiy.sirenordersystem.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeidiiy.sirenordersystem.order.domain.Order;
import io.jeidiiy.sirenordersystem.order.domain.OrderStatus;
import io.jeidiiy.sirenordersystem.order.domain.dto.*;
import io.jeidiiy.sirenordersystem.order.repository.OrderJpaRepository;
import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;
import io.jeidiiy.sirenordersystem.product.domain.dto.ProductDto;
import io.jeidiiy.sirenordersystem.product.domain.food.dto.FoodDto;
import io.jeidiiy.sirenordersystem.product.service.ProductService;
import io.jeidiiy.sirenordersystem.store.domain.Store;
import io.jeidiiy.sirenordersystem.store.service.StoreService;
import io.jeidiiy.sirenordersystem.user.domain.User;
import io.jeidiiy.sirenordersystem.user.service.UserService;
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
                new OrderProductDto(
                    americanoId, americanoQuantity, Category.BEVERAGE),
                new OrderProductDto(sandwichId, sandwichQuantity, Category.FOOD)));

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
}
