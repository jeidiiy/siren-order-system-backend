package io.jeidiiy.sirenordersystem.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.Type;
import io.jeidiiy.sirenordersystem.product.domain.dto.TypeResponseDto;
import io.jeidiiy.sirenordersystem.product.repository.TypeJpaRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("[Service] 종류 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class TypeServiceTest {
  @InjectMocks TypeService sut;

  @Mock TypeJpaRepository typeJpaRepository;

  private List<Type> types;

  @BeforeEach
  void setup() {
    types =
        List.of(
            Type.of("NEW", "", Category.BEVERAGE),
            Type.of("추천", "Recommend", Category.BEVERAGE),
            Type.of("에스프레소", "Espresso", Category.BEVERAGE),
            Type.of("NEW", "", Category.FOOD),
            Type.of("추천", "Recommend", Category.FOOD),
            Type.of("샌드위치", "Sandwich", Category.FOOD),
            Type.of("NEW", "", Category.MERCHANDISE),
            Type.of("추천", "Recommend", Category.MERCHANDISE),
            Type.of("머그/글라스", "Mug/Glass", Category.MERCHANDISE));
  }

  @DisplayName("카테고리로 종류 목록을 조회한다.")
  @Test
  void givenCategory_whenFinding_thenReturnTypeResponseDtoList() {
    // given
    Category category = Category.valueOf("BEVERAGE");
    given(typeJpaRepository.findAllByCategory(category))
        .willReturn(List.of(types.get(0), types.get(1), types.get(2)));

    // when
    List<TypeResponseDto> result = sut.findTypeByCategory(category);

    // then
    assertThat(result).hasSize(3);
    assertThat(result.get(2).title()).isEqualTo(types.get(2).getTitle());
    then(typeJpaRepository).should().findAllByCategory(category);
  }
}
