package io.jeidiiy.sirenordersystem.cart.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import io.jeidiiy.sirenordersystem.cart.domain.Cart;
import io.jeidiiy.sirenordersystem.cart.domain.dto.CartPostRequestDto;
import io.jeidiiy.sirenordersystem.cart.domain.dto.CartResponseDto;
import io.jeidiiy.sirenordersystem.cart.repository.CartJpaRepository;
import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.Product;
import io.jeidiiy.sirenordersystem.product.service.ProductService;
import io.jeidiiy.sirenordersystem.user.domain.User;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("[Service] 주문 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class CartServiceTest {
  @InjectMocks CartService sut;

  @Mock CartJpaRepository cartJpaRepository;
  @Mock UserService userService;
  @Mock ProductService productService;

  @BeforeEach
  void setUp() {}

  @DisplayName("로그인한 사용자의 장바구니 목록을 조회한다.")
  @Test
  void givenUsername_whenFindingCart_thenRespondsCart() {
    // given
    String username = "loginUsername";
    User user = User.builder().username(username).build();
    var userId = 1;
    ReflectionTestUtils.setField(user, "id", userId);

    Product americano =
        Product.of(1, "아메리카노", "Americano", "아메리카노", 4500, "아메리카노 이미지", Category.BEVERAGE);
    Product sandwich =
        Product.of(2, "샌드위치", "sandwich", "샌드위치 설명", 6700, "샌드위치 이미지", Category.FOOD);

    Cart cart1 = Cart.of(user, americano);
    Cart cart2 = Cart.of(user, sandwich);

    List<Cart> carts = List.of(cart1, cart2);

    given(userService.getUserByUsername(username)).willReturn(user);
    given(cartJpaRepository.findAllByUserId(userId)).willReturn(carts);

    // when
    List<CartResponseDto> result = sut.findAllByUsername(username);

    // then
    assertThat(result).hasSize(2);
  }

  @DisplayName("장바구니에 상품을 추가한다.")
  @Test
  void givenNewProduct_whenAdding_thenSucceeds() {
    // given
    String username = "loginUsername";
    User user = User.builder().username(username).build();
    var userId = 1;
    ReflectionTestUtils.setField(user, "id", userId);

    var productId = 1;
    Product mug = Product.of(productId, "머그컵", "Mug", "머그컵", 4500, "머그컵 이미지", Category.MERCHANDISE);

    Cart cart1 = Cart.of(user, mug);
    Integer cart1Id = 1;
    ReflectionTestUtils.setField(cart1, "id", cart1Id);

    CartPostRequestDto requestDto = new CartPostRequestDto(null, productId, null);
    CartResponseDto expectedResponse =
        new CartResponseDto(cart1Id, 1, mug.getBasePrice(), mug.getKrName());
    List<CartResponseDto> expectedResponseList = List.of(expectedResponse);

    given(userService.getUserByUsername(username)).willReturn(user);
    given(productService.findById(productId)).willReturn(mug);
    given(cartJpaRepository.save(cart1)).willReturn(cart1);
    given(sut.findAllByUsername(username)).willReturn(expectedResponseList);
    given(cartJpaRepository.findAllByUserId(userId)).willReturn(List.of(cart1));

    // when
    List<CartResponseDto> result = sut.upsert(username, requestDto);

    // then
    assertThat(result).hasSize(1);
  }

  @DisplayName("장바구니에 있는 상품의 수량을 변경한다.")
  @Test
  void givenQuantity_whenChanging_thenSucceeds() {
    // given
    String username = "loginUsername";
    User user = User.builder().username(username).build();
    var userId = 1;
    ReflectionTestUtils.setField(user, "id", userId);

    Product americano =
        Product.of(1, "아메리카노", "Americano", "아메리카노", 4500, "아메리카노 이미지", Category.BEVERAGE);
    Product sandwich =
        Product.of(2, "샌드위치", "sandwich", "샌드위치 설명", 6700, "샌드위치 이미지", Category.FOOD);

    Cart cart1 = Cart.of(user, americano);
    Integer cart1Id = 1;
    ReflectionTestUtils.setField(cart1, "id", cart1Id);
    Cart cart2 = Cart.of(user, sandwich);
    Integer cart2Id = 2;
    ReflectionTestUtils.setField(cart2, "id", cart2Id);

    int updatedQuantity = 2;
    CartPostRequestDto requestDto =
        new CartPostRequestDto(cart1Id, americano.getId(), updatedQuantity);

    CartResponseDto expectedResponse1 =
        new CartResponseDto(
            cart1Id,
            updatedQuantity,
            americano.getBasePrice() * updatedQuantity,
            americano.getKrName());
    CartResponseDto expectedResponse2 =
        new CartResponseDto(
            cart2Id,
            cart2.getQuantity(),
            sandwich.getBasePrice() * cart2.getQuantity(),
            sandwich.getKrName());
    List<CartResponseDto> expectedResponseList = List.of(expectedResponse1, expectedResponse2);

    given(userService.getUserByUsername(username)).willReturn(user);
    given(cartJpaRepository.findById(cart1Id)).willReturn(Optional.of(cart1));
    given(productService.findById(americano.getId())).willReturn(americano);
    given(sut.findAllByUsername(username)).willReturn(expectedResponseList);
    given(cartJpaRepository.findAllByUserId(userId)).willReturn(List.of(cart1, cart2));

    // when
    List<CartResponseDto> result = sut.upsert(username, requestDto);

    // then
    CartResponseDto americanoResponseDto = result.get(0);
    assertThat(result).hasSize(2);
    assertThat(americanoResponseDto.quantity()).isEqualTo(updatedQuantity);
    assertThat(americanoResponseDto.price()).isEqualTo(americano.getBasePrice() * updatedQuantity);
  }

  @DisplayName("장바구니에 있는 특정 상품을 삭제한다.")
  @Test
  void givenCartId_whenDeleting_thenSucceeds() {
    // given
    String username = "loginUsername";
    User user = User.builder().username(username).build();
    var userId = 1;
    ReflectionTestUtils.setField(user, "id", userId);

    Product americano =
        Product.of(1, "아메리카노", "Americano", "아메리카노", 4500, "아메리카노 이미지", Category.BEVERAGE);
    Product sandwich =
        Product.of(2, "샌드위치", "sandwich", "샌드위치 설명", 6700, "샌드위치 이미지", Category.FOOD);

    Cart cart1 = Cart.of(user, americano);
    Integer cart1Id = 1;
    ReflectionTestUtils.setField(cart1, "id", cart1Id);
    Cart cart2 = Cart.of(user, sandwich);
    Integer cart2Id = 2;
    ReflectionTestUtils.setField(cart2, "id", cart2Id);

    CartResponseDto expectedResponse =
        new CartResponseDto(
            cart2Id, cart2.getQuantity(), sandwich.getBasePrice(), sandwich.getKrName());
    List<CartResponseDto> expectedResponseList = List.of(expectedResponse);

    willDoNothing().given(cartJpaRepository).deleteById(cart1Id);
    given(userService.getUserByUsername(username)).willReturn(user);
    given(sut.findAllByUsername(username)).willReturn(expectedResponseList);
    given(cartJpaRepository.findAllByUserId(userId)).willReturn(List.of(cart2));

    // when
    List<CartResponseDto> result = sut.remove(username, cart1Id);

    // then
    assertThat(result).hasSize(1);
  }

  @DisplayName("장바구니에 있는 모든 상품을 삭제한다.")
  @Test
  void givenNothing_whenDeleting_thenSucceeds() {
    // given
    String username = "loginUsername";
    User user = User.builder().username(username).build();
    var userId = 1;
    ReflectionTestUtils.setField(user, "id", userId);

    Product americano =
        Product.of(1, "아메리카노", "Americano", "아메리카노", 4500, "아메리카노 이미지", Category.BEVERAGE);
    Product sandwich =
        Product.of(2, "샌드위치", "sandwich", "샌드위치 설명", 6700, "샌드위치 이미지", Category.FOOD);

    Cart cart1 = Cart.of(user, americano);
    Integer cart1Id = 1;
    ReflectionTestUtils.setField(cart1, "id", cart1Id);
    Cart cart2 = Cart.of(user, sandwich);
    Integer cart2Id = 2;
    ReflectionTestUtils.setField(cart2, "id", cart2Id);

    given(userService.getUserByUsername(username)).willReturn(user);
    willDoNothing().given(cartJpaRepository).deleteAllByUserId(userId);
    given(sut.findAllByUsername(username)).willReturn(List.of());

    // when
    List<CartResponseDto> result = sut.removeAll(username);

    // then
    assertThat(result).hasSize(0);
  }
}
