package io.jeidiiy.sirenordersystem.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeidiiy.sirenordersystem.order.domain.Order;
import io.jeidiiy.sirenordersystem.order.domain.OrderProduct;
import io.jeidiiy.sirenordersystem.order.domain.OrderStatus;
import io.jeidiiy.sirenordersystem.order.domain.dto.*;
import io.jeidiiy.sirenordersystem.order.repository.OrderJpaRepository;
import io.jeidiiy.sirenordersystem.product.domain.Product;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;
import io.jeidiiy.sirenordersystem.product.domain.dto.ProductDto;
import io.jeidiiy.sirenordersystem.product.service.ProductService;
import io.jeidiiy.sirenordersystem.store.domain.Store;
import io.jeidiiy.sirenordersystem.store.service.StoreService;
import io.jeidiiy.sirenordersystem.user.domain.User;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import java.util.Map;
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
  private final ProductDtoResolver productDtoResolver;
  private final ObjectMapper mapper;

  public void createOrder(OrderPostRequestBody requestBody, String currentUsername) {
    User user = userService.getUserByUsername(currentUsername);
    Store store = storeService.findById(requestBody.storeId());
    Order order = Order.of(user, store, OrderStatus.PENDING);
    int totalPrice = 0;

    for (OrderProductDto item : requestBody.orderProductDtos()) {
      ProductDto productDto = null;
      OrderProduct orderProduct = null;
      switch (item.category()) {
        case BEVERAGE -> {
          BeverageOptionDto beverageOptionDto =
              mapper.convertValue(item.options(), BeverageOptionDto.class);
          productDto =
              productDtoResolver.resolveBeverageItemDto(item.productId(), beverageOptionDto);
          Product product = productDto.toEntity();
          orderProduct = OrderProduct.of(order, product, item.quantity());
          orderProduct.setOptionsFromMap(
              BeverageOptionJsonMapper.convertDecoratedBeverageToMap((BeverageDto) productDto));
        }
        case FOOD -> {
          FoodOptionDto foodOptionDto = mapper.convertValue(item.options(), FoodOptionDto.class);
          productDto = productDtoResolver.resolveFoodItemDto(item.productId(), foodOptionDto);
          Product product = productDto.toEntity();
          orderProduct = OrderProduct.of(order, product, item.quantity());
          orderProduct.setOptionsFromMap(Map.of("warming", foodOptionDto.warming()));
        }
        case MERCHANDISE -> {
          MerchandiseOptionDto merchandiseOptionDto =
              mapper.convertValue(item.options(), MerchandiseOptionDto.class);
          productDto =
              productDtoResolver.resolveMerchandiseItemDto(item.productId(), merchandiseOptionDto);
          Product product = productDto.toEntity();
          orderProduct = OrderProduct.of(order, product, item.quantity());
          orderProduct.setOptionsFromMap(Map.of("packaging", merchandiseOptionDto.option()));
        }
      }
      totalPrice += productDto.getBasePrice() * item.quantity();
      orderProductService.save(orderProduct);
    }

    order.setTotalPrice(totalPrice);
    orderJpaRepository.save(order);
  }
}
