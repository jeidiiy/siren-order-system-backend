package io.jeidiiy.sirenordersystem.product.repository;

import io.jeidiiy.sirenordersystem.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Integer> {
  @Query("SELECT p FROM Product p JOIN ProductType tp ON tp.product.productId = p.productId WHERE tp.type.typeId = :typeId")
  List<Product> findAllByTypeProductsTypeId(@Param("typeId") Integer typeId);
}
