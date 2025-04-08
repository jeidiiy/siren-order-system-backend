package io.jeidiiy.sirenordersystem.product.repository;

import io.jeidiiy.sirenordersystem.product.domain.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductJpaRepository extends JpaRepository<Product, Integer> {
  @Query(
      "SELECT p FROM Product p JOIN ProductType tp ON tp.product.id = p.id WHERE tp.type.id = :typeId")
  List<Product> findAllByTypeProductsTypeId(@Param("typeId") Integer typeId);
}
