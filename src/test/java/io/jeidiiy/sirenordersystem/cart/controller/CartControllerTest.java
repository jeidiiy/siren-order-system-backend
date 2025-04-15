package io.jeidiiy.sirenordersystem.cart.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeidiiy.sirenordersystem.cart.domain.Cart;
import io.jeidiiy.sirenordersystem.cart.domain.dto.CartPostRequestDto;
import io.jeidiiy.sirenordersystem.cart.domain.dto.CartResponseDto;
import io.jeidiiy.sirenordersystem.cart.service.CartService;
import io.jeidiiy.sirenordersystem.config.SecurityConfig;
import io.jeidiiy.sirenordersystem.jwt.service.JwtAuthenticationEntryPoint;
import io.jeidiiy.sirenordersystem.jwt.service.JwtLogoutSuccessHandler;
import io.jeidiiy.sirenordersystem.jwt.service.JwtService;
import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.Product;
import io.jeidiiy.sirenordersystem.security.WithMockCustomUser;
import io.jeidiiy.sirenordersystem.user.domain.User;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[Controller] 장바구니 컨트롤러 테스트")
@Import({SecurityConfig.class, JwtService.class})
@WebMvcTest(CartController.class)
class CartControllerTest {
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper mapper;
  @Autowired JwtService jwtService;

  @MockitoBean CartService cartService;
  @MockitoBean UserService userService;
  @MockitoBean JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  @MockitoBean JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

  @DisplayName("[GET] 사용자가 자신의 장바구니 요청 -> 200 OK [성공]")
  @WithMockCustomUser
  @Test
  void givenUsername_whenRequesting_thenRespondsCart() throws Exception {
    // given
    String username = "loginUsername";
    User user = User.builder().username(username).build();
    ReflectionTestUtils.setField(user, "id", 1);

    Product americano =
        Product.of(1, "아메리카노", "Americano", "아메리카노", 4500, "아메리카노 이미지", Category.BEVERAGE);
    Product sandwich =
        Product.of(2, "샌드위치", "sandwich", "샌드위치 설명", 6700, "샌드위치 이미지", Category.FOOD);

    Cart cart1 = Cart.of(user, americano);
    Cart cart2 = Cart.of(user, sandwich);

    List<CartResponseDto> expected =
        List.of(CartResponseDto.fromEntity(cart1), CartResponseDto.fromEntity(cart2));

    given(cartService.findAllByUsername(username)).willReturn(expected);

    // when & then
    mvc.perform(get("/api/v1/carts/" + username))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @DisplayName("[POST] 사용자 장바구니 업데이트 요청 -> 200 OK [성공]")
  @WithMockCustomUser
  @Test
  void givenUsernameAndCartElem_whenRequesting_thenRespondsCart() throws Exception {
    // given
    String username = "loginUsername";
    User user = User.builder().username(username).build();
    ReflectionTestUtils.setField(user, "id", 1);

    Product americano =
        Product.of(1, "아메리카노", "Americano", "아메리카노", 4500, "아메리카노 이미지", Category.BEVERAGE);

    Integer cart1Id = 1;
    Cart cart1 = Cart.of(user, americano);
    ReflectionTestUtils.setField(cart1, "id", cart1Id);

    CartPostRequestDto requestDto = new CartPostRequestDto(cart1Id, americano.getId(), 2);

    int updatedQuantity = cart1.getQuantity() + 1;
    CartResponseDto expectedCartResponse =
        new CartResponseDto(cart1Id, updatedQuantity, cart1.getPrice(), americano.getKrName());
    List<CartResponseDto> expectedResponse = List.of(expectedCartResponse);

    given(cartService.upsert(username, requestDto)).willReturn(expectedResponse);

    // when & then
    mvc.perform(
            post("/api/v1/carts/" + username)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(mapper.writeValueAsString(expectedResponse)));
  }

  @DisplayName("[POST] 사용자 장바구니에 목록 추가 요청 -> 200 OK [성공]")
  @WithMockCustomUser
  @Test
  void givenUsernameAndNewCartElem_whenRequesting_thenRespondsCart() throws Exception {
    // given
    String username = "loginUsername";
    User user = User.builder().username(username).build();
    ReflectionTestUtils.setField(user, "id", 1);

    Product americano =
        Product.of(1, "아메리카노", "Americano", "아메리카노", 4500, "아메리카노 이미지", Category.BEVERAGE);

    Integer cart1Id = 1;
    Cart cart1 = Cart.of(user, americano);
    ReflectionTestUtils.setField(cart1, "id", cart1Id);

    CartPostRequestDto requestDto = new CartPostRequestDto(null, americano.getId(), null);

    CartResponseDto expectedCartResponse =
        new CartResponseDto(cart1Id, 1, cart1.getPrice(), americano.getKrName());
    List<CartResponseDto> expectedResponse = List.of(expectedCartResponse);

    given(cartService.upsert(username, requestDto)).willReturn(expectedResponse);

    // when & then
    mvc.perform(
            post("/api/v1/carts/" + username)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(mapper.writeValueAsString(expectedResponse)))
        .andExpect(jsonPath("$[0].quantity").value(1))
        .andExpect(jsonPath("$[0].price").value(americano.getBasePrice()));
  }

  @DisplayName("[DELETE] 사용자 장바구니에서 특정 목록 삭제 요청 -> 200 OK [성공]")
  @WithMockCustomUser
  @Test
  void givenUsernameAndCartId_whenRequesting_thenRespondsEmptyCart() throws Exception {
    // given
    String username = "loginUsername";
    User user = User.builder().username(username).build();
    ReflectionTestUtils.setField(user, "id", 1);

    Product americano =
        Product.of(1, "아메리카노", "Americano", "아메리카노", 4500, "아메리카노 이미지", Category.BEVERAGE);
    Product sandwich =
        Product.of(2, "샌드위치", "sandwich", "샌드위치 설명", 6700, "샌드위치 이미지", Category.FOOD);

    Integer cart1Id = 1;
    Cart cart1 = Cart.of(user, americano);
    ReflectionTestUtils.setField(cart1, "id", cart1Id);
    Integer cart2Id = 2;
    Cart cart2 = Cart.of(user, sandwich);
    ReflectionTestUtils.setField(cart2, "id", cart2Id);

    List<CartResponseDto> expectedResponse = List.of(CartResponseDto.fromEntity(cart1));

    given(cartService.remove(username, cart2Id)).willReturn(expectedResponse);

    // when & then
    mvc.perform(
            delete("/api/v1/carts/" + username + "/" + cart2Id)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(content().json(mapper.writeValueAsString(expectedResponse)));
  }

  @DisplayName("[DELETE] 사용자 장바구니에서 전체 목록 삭제 요청 -> 200 OK [성공]")
  @WithMockCustomUser
  @Test
  void givenUsername_whenRequesting_thenRespondsEmptyCart() throws Exception {
    // given
    String username = "loginUsername";
    User user = User.builder().username(username).build();
    ReflectionTestUtils.setField(user, "id", 1);

    Product americano =
        Product.of(1, "아메리카노", "Americano", "아메리카노", 4500, "아메리카노 이미지", Category.BEVERAGE);
    Product sandwich =
        Product.of(2, "샌드위치", "sandwich", "샌드위치 설명", 6700, "샌드위치 이미지", Category.FOOD);

    Integer cart1Id = 1;
    Cart cart1 = Cart.of(user, americano);
    ReflectionTestUtils.setField(cart1, "id", cart1Id);
    Integer cart2Id = 2;
    Cart cart2 = Cart.of(user, sandwich);
    ReflectionTestUtils.setField(cart2, "id", cart2Id);

    List<CartResponseDto> expectedResponse = List.of();

    given(cartService.removeAll(username)).willReturn(expectedResponse);

    // when & then
    mvc.perform(delete("/api/v1/carts/" + username).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(0)))
        .andExpect(content().json(mapper.writeValueAsString(expectedResponse)));
  }
}
