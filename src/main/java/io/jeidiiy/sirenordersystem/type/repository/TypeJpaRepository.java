package io.jeidiiy.sirenordersystem.type.repository;

import io.jeidiiy.sirenordersystem.type.domain.Category;
import io.jeidiiy.sirenordersystem.type.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeJpaRepository extends JpaRepository<Type, Integer> {
  List<Type> findAllByCategory(Category category);
}
