package io.jeidiiy.sirenordersystem.product.service;

import io.jeidiiy.sirenordersystem.product.domain.Product;
import io.jeidiiy.sirenordersystem.product.domain.dto.ProductResponseDto;
import io.jeidiiy.sirenordersystem.product.exception.ProductNotFoundException;
import io.jeidiiy.sirenordersystem.product.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {
  private final ProductJpaRepository productJpaRepository;

  public List<ProductResponseDto> findAllByTypeId(Integer typeId) {
    List<Product> products = productJpaRepository.findAllByTypeProductsTypeId(typeId);
    if (products.isEmpty()) {
      throw new IllegalArgumentException("해당 종류에 해당하는 상품은 없습니다. Type Id : " + typeId);
    }
    return products.stream().map(ProductResponseDto::from).toList();
  }

  public Product findById(Integer productId) {
    return productJpaRepository
        .findById(productId)
        .orElseThrow(() -> new ProductNotFoundException("해당 상품이 없습니다. ID : " + productId));
  }

  public void save(Product product) {
    productJpaRepository.save(product);
  }
}
