package io.jeidiiy.sirenordersystem.order.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeidiiy.sirenordersystem.config.SecurityConfig;
import io.jeidiiy.sirenordersystem.jwt.service.JwtAuthenticationEntryPoint;
import io.jeidiiy.sirenordersystem.jwt.service.JwtLogoutSuccessHandler;
import io.jeidiiy.sirenordersystem.jwt.service.JwtService;
import io.jeidiiy.sirenordersystem.order.domain.dto.OrderPostRequestBody;
import io.jeidiiy.sirenordersystem.order.domain.dto.OrderProductDto;
import io.jeidiiy.sirenordersystem.order.service.OrderService;
import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;
import io.jeidiiy.sirenordersystem.product.domain.dto.ProductDto;
import io.jeidiiy.sirenordersystem.product.domain.food.dto.FoodDto;
import io.jeidiiy.sirenordersystem.security.WithMockCustomUser;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[Controller] 주문 컨트롤러 테스트")
@Import({SecurityConfig.class, JwtService.class})
@WebMvcTest(OrderController.class)
class OrderControllerTest {
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper mapper;
  @Autowired JwtService jwtService;

  @MockitoBean OrderService orderService;
  @MockitoBean UserService userService;
  @MockitoBean JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  @MockitoBean JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

  @DisplayName("[POST] 사용자가 매장 및 수령 방법, 주문 상품 및 개수를 요청 -> 200 OK [성공]")
  @WithMockCustomUser
  @Test
  void givenStoreAndPickupOptionAndProducts_whenRequesting_thenResponds200()
      throws Exception {
    // given
    int storeId = 1;
    String pickupOption = "to-go";
    int americanoId = 1;
    int americanoQuantity = 2;
    BeverageDto americano =
        (BeverageDto) ProductDto.of(americanoId, "아메리카노", "Americano", "아메리카노", 4500, "아메리카노 이미지", Category.BEVERAGE);

    int sandwichId = 2;
    int sandwichQuantity = 2;
    FoodDto sandwich =
        (FoodDto) ProductDto.of(sandwichId, "샌드위치", "sandwich", "샌드위치 설명", 6700, "샌드위치 이미지", Category.FOOD);

    OrderPostRequestBody orderPostRequestBody =
        new OrderPostRequestBody(
            storeId,
            pickupOption,
            List.of(
                new OrderProductDto(
                    americanoId, americanoQuantity, Category.BEVERAGE),
                new OrderProductDto(sandwichId, sandwichQuantity, Category.FOOD)));

    // when & then
    mvc.perform(
            post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(orderPostRequestBody)))
        .andExpect(status().isOk());
  }
}
