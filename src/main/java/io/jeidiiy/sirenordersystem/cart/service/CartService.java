package io.jeidiiy.sirenordersystem.cart.service;

import io.jeidiiy.sirenordersystem.cart.domain.Cart;
import io.jeidiiy.sirenordersystem.cart.domain.dto.CartPostRequestDto;
import io.jeidiiy.sirenordersystem.cart.domain.dto.CartResponseDto;
import io.jeidiiy.sirenordersystem.cart.exception.NonExistCartException;
import io.jeidiiy.sirenordersystem.cart.repository.CartJpaRepository;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;
import io.jeidiiy.sirenordersystem.product.domain.Product;
import io.jeidiiy.sirenordersystem.product.service.ProductService;
import io.jeidiiy.sirenordersystem.user.domain.User;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CartService {

  private final CartJpaRepository cartJpaRepository;
  private final UserService userService;
  private final ProductService productService;

  @Transactional(readOnly = true)
  public List<CartResponseDto> findAllByUsername(String username) {
    User user = userService.getUserByUsername(username);
    List<Cart> carts = cartJpaRepository.findAllByUserId(user.getId());
    return carts.stream().map(CartResponseDto::fromEntity).toList();
  }

  public List<CartResponseDto> upsert(String username, CartPostRequestDto cartPostRequestDto) {
    if (cartPostRequestDto.cartId() == null) {
      User user = userService.getUserByUsername(username);
      Product product = productService.findById(cartPostRequestDto.productId());
      cartJpaRepository.save(Cart.of(user, product));
    } else {
      Optional<Cart> optionalCart = cartJpaRepository.findById(cartPostRequestDto.cartId());

      if (optionalCart.isEmpty()) {
        throw new NonExistCartException(ErrorCode.CART_NOT_FOUND);
      }

      Cart cart = optionalCart.get();
      cart.setQuantity(cartPostRequestDto.quantity());
      cartJpaRepository.save(cart);
    }

    return findAllByUsername(username);
  }

  public List<CartResponseDto> remove(String username, Integer cartId) {
    cartJpaRepository.deleteById(cartId);
    return findAllByUsername(username);
  }

  public List<CartResponseDto> removeAll(String username) {
    User user = userService.getUserByUsername(username);
    cartJpaRepository.deleteAllByUserId(user.getId());
    return findAllByUsername(username);
  }
}
