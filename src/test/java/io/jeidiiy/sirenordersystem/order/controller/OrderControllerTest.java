package io.jeidiiy.sirenordersystem.order.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeidiiy.sirenordersystem.config.SecurityConfig;
import io.jeidiiy.sirenordersystem.jwt.filter.JwtAuthenticationFilter;
import io.jeidiiy.sirenordersystem.jwt.service.JwtAuthenticationEntryPoint;
import io.jeidiiy.sirenordersystem.jwt.service.JwtLogoutSuccessHandler;
import io.jeidiiy.sirenordersystem.jwt.service.JwtService;
import io.jeidiiy.sirenordersystem.order.domain.dto.BeverageOptionJsonMapper;
import io.jeidiiy.sirenordersystem.order.domain.dto.FoodOptionDto;
import io.jeidiiy.sirenordersystem.order.domain.dto.OrderPostRequestBody;
import io.jeidiiy.sirenordersystem.order.domain.dto.OrderProductDto;
import io.jeidiiy.sirenordersystem.order.service.OrderService;
import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.bean.Bean;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.bean.BeanType;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.cup.Cup;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.cup.CupSize;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.etc.Etc;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.shot.Shot;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.syrup.Syrup;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.syrup.SyrupType;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.temperature.Level;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.temperature.Temperature;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;
import io.jeidiiy.sirenordersystem.product.domain.dto.ProductDto;
import io.jeidiiy.sirenordersystem.product.domain.food.dto.FoodDto;
import io.jeidiiy.sirenordersystem.security.WithMockCustomUser;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import java.util.List;
import java.util.Map;
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
  void givenStoreAndPickupOptionAndProductsWithOptions_whenRequesting_thenResponds200()
      throws Exception {
    // given
    int storeId = 1;
    String pickupOption = "to-go";
    int americanoId = 1;
    int americanoQuantity = 2;
    BeverageDto americano =
        (BeverageDto) ProductDto.of(americanoId, "아메리카노", "Americano", "아메리카노", 4500, "아메리카노 이미지", Category.BEVERAGE);
    americano = new Bean(americano, BeanType.SIGNATURE);
    americano = new Cup(americano, CupSize.TALL);
    americano = new Shot(americano, 2);
    americano = new Temperature(americano, Level.WARM);
    americano = new Syrup(americano, SyrupType.VANILLA, 1);
    americano = new Syrup(americano, SyrupType.HAZELNUT, 2);
    americano = new Etc(americano, "여유 공간 남겨주세요");

    int sandwichId = 2;
    int sandwichQuantity = 2;
    FoodDto sandwich =
        (FoodDto) ProductDto.of(sandwichId, "샌드위치", "sandwich", "샌드위치 설명", 6700, "샌드위치 이미지", Category.FOOD);
    FoodOptionDto foodOptionDto = new FoodOptionDto("warming");

    Map<String, Object> beverageOptions =
        BeverageOptionJsonMapper.convertDecoratedBeverageToMap(americano);
    OrderPostRequestBody orderPostRequestBody =
        new OrderPostRequestBody(
            storeId,
            pickupOption,
            List.of(
                new OrderProductDto(
                    americanoId, americanoQuantity, Category.BEVERAGE, beverageOptions),
                new OrderProductDto(sandwichId, sandwichQuantity, Category.FOOD, foodOptionDto)));

    // when & then
    mvc.perform(
            post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(orderPostRequestBody)))
        .andExpect(status().isOk());
  }
}
