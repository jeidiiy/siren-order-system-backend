package io.jeidiiy.sirenordersystem.product.repository;

import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeJpaRepository extends JpaRepository<Type, Integer> {
  List<Type> findAllByCategory(Category category);
}
