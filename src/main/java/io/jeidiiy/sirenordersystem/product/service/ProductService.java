package io.jeidiiy.sirenordersystem.product.service;

import io.jeidiiy.sirenordersystem.exception.ErrorCode;
import io.jeidiiy.sirenordersystem.product.domain.Product;
import io.jeidiiy.sirenordersystem.product.domain.dto.ProductResponseDto;
import io.jeidiiy.sirenordersystem.product.exception.ProductNotFoundException;
import io.jeidiiy.sirenordersystem.product.exception.ProductNotFoundInTypeException;
import io.jeidiiy.sirenordersystem.product.repository.ProductJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {
  private final ProductJpaRepository productJpaRepository;

  public List<ProductResponseDto> findAllByTypeId(Integer typeId) {
    List<Product> products = productJpaRepository.findAllByTypeProductsTypeId(typeId);
    if (products.isEmpty()) {
      throw new ProductNotFoundInTypeException(ErrorCode.PRODUCT_NOT_FOUND_IN_TYPE);
    }
    return products.stream().map(ProductResponseDto::from).toList();
  }

  public Product findById(Integer productId) {
    return productJpaRepository
        .findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
  }

  public void save(Product product) {
    productJpaRepository.save(product);
  }
}
