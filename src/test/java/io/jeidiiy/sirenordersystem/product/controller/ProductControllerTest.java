package io.jeidiiy.sirenordersystem.product.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeidiiy.sirenordersystem.config.SecurityConfig;
import io.jeidiiy.sirenordersystem.jwt.filter.JwtAuthenticationFilter;
import io.jeidiiy.sirenordersystem.jwt.service.JwtAuthenticationEntryPoint;
import io.jeidiiy.sirenordersystem.jwt.service.JwtLogoutSuccessHandler;
import io.jeidiiy.sirenordersystem.jwt.service.JwtService;
import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.Product;
import io.jeidiiy.sirenordersystem.product.domain.ProductType;
import io.jeidiiy.sirenordersystem.product.domain.Type;
import io.jeidiiy.sirenordersystem.product.domain.dto.ProductResponseDto;
import io.jeidiiy.sirenordersystem.product.service.ProductService;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[Controller] 상품 컨트롤러 테스트")
@Import({SecurityConfig.class, JwtService.class})
@WebMvcTest(ProductController.class)
class ProductControllerTest {
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper mapper;
  @Autowired JwtService jwtService;

  @MockitoBean ProductService productService;
  @MockitoBean UserService userService;
  @MockitoBean JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  @MockitoBean JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

  private List<Product> products;

  @BeforeEach
  void setup() {
    products =
        List.of(
            Product.of(1, "아메리카노", "Americano", "쌉싸름한 커피", 4500, "아메리카노이미지", Category.BEVERAGE),
            Product.of(2, "카페라떼", "Caffe Latte", "고소한 커피", 5000, "카페라떼이미지", Category.BEVERAGE),
            Product.of(3, "샌드위치", "Sandwich", "샌드위치", 5700, "샌드위치이미지", Category.FOOD));
    // 아래 데이터들은 테스트에 영향은 없지만 관계를 나타내기 위해 작성함
    List<Type> types =
        List.of(
            Type.of("NEW", "", Category.BEVERAGE),
            Type.of("추천", "Recommend", Category.BEVERAGE),
            Type.of("에스프레소", "Espresso", Category.BEVERAGE));
    ProductType productType1 = new ProductType();
    ReflectionTestUtils.setField(productType1, "id", 1);
    ReflectionTestUtils.setField(productType1, "type", types.get(2)); // 에스프레소
    ReflectionTestUtils.setField(productType1, "product", products.get(0)); // 아메리카노
    ProductType productType2 = new ProductType();
    ReflectionTestUtils.setField(productType2, "id", 2);
    ReflectionTestUtils.setField(productType2, "type", types.get(1)); // 추천
    ReflectionTestUtils.setField(productType2, "product", products.get(0)); // 아메리카노
    ProductType productType3 = new ProductType();
    ReflectionTestUtils.setField(productType3, "id", 2);
    ReflectionTestUtils.setField(productType3, "type", types.get(0)); // NEW
    ReflectionTestUtils.setField(productType3, "product", products.get(1)); // 카페라떼
    ProductType productType4 = new ProductType();
    ReflectionTestUtils.setField(productType4, "id", 2);
    ReflectionTestUtils.setField(productType4, "type", types.get(2)); // 에스프레소
    ReflectionTestUtils.setField(productType4, "product", products.get(1)); // 카페라떼
    List<ProductType> productTypes =
        List.of(productType1, productType2, productType3, productType4);
  }

  @DisplayName("[GET] 종류로 메뉴 조회하기 -> 200 OK [성공]")
  @Test
  void givenType_whenRequesting_thenRespondsMenus() throws Exception {
    // given
    int typeId = 1; // 에스프레소

    given(productService.findAllByTypeId(typeId))
        .willReturn(
            List.of(
                ProductResponseDto.from(products.get(0)),
                ProductResponseDto.from(products.get(1))));

    // when & then
    mvc.perform(get("/api/v1/products?typeId=" + typeId)).andExpect(status().isOk());
    then(productService).should().findAllByTypeId(typeId);
  }

  @DisplayName("[GET] 없는 종류로 요청 시 -> 400 BAD REQUEST [실패]")
  @Test
  void givenNonExistsType_whenRequesting_thenResponds400() throws Exception {
    // given
    int nonExistsTypeId = 9999;

    willThrow(IllegalArgumentException.class)
        .given(productService)
        .findAllByTypeId(nonExistsTypeId);

    // when & then
    mvc.perform(get("/api/v1/products?typeId=" + nonExistsTypeId))
        .andExpect(status().isBadRequest());
    then(productService).should().findAllByTypeId(nonExistsTypeId);
  }
}
