package io.jeidiiy.sirenordersystem.type.service;

import io.jeidiiy.sirenordersystem.type.domain.Category;
import io.jeidiiy.sirenordersystem.type.domain.dto.TypeResponseDto;
import io.jeidiiy.sirenordersystem.type.repository.TypeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class TypeService {

  private final TypeJpaRepository typeJpaRepository;

  @Transactional(readOnly = true)
  public List<TypeResponseDto> findTypeByCategory(Category category) {
    return typeJpaRepository.findAllByCategory(category).stream()
        .map(TypeResponseDto::from)
        .toList();
  }
}
