package io.jeidiiy.sirenordersystem.product.service;

import static io.jeidiiy.sirenordersystem.exception.ErrorCode.PRODUCT_NOT_FOUND_IN_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.mockito.BDDMockito.*;

import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.Product;
import io.jeidiiy.sirenordersystem.product.domain.ProductType;
import io.jeidiiy.sirenordersystem.product.domain.Type;
import io.jeidiiy.sirenordersystem.product.domain.dto.ProductResponseDto;
import io.jeidiiy.sirenordersystem.product.exception.ProductNotFoundInTypeException;
import io.jeidiiy.sirenordersystem.product.repository.ProductJpaRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("[Service] 상품 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
  @InjectMocks private ProductService sut;

  @Mock ProductJpaRepository productJpaRepository;

  private List<Product> products;

  @BeforeEach
  void setup() {
    products =
        List.of(
            Product.of(1, "아메리카노", "Americano", "쌉싸름한 커피", 4500, "아메리카노이미지", Category.BEVERAGE),
            Product.of(2, "카페라떼", "Caffe Latte", "고소한 커피", 5000, "카페라떼이미지", Category.BEVERAGE),
            Product.of(3, "샌드위치", "Sandwich", "샌드위치", 5700, "샌드위치이미지", Category.FOOD));
    // 아래 데이터들은 테스트에 영향은 없지만 관계를 나타내기 위해 작성함
    List<Type> types =
        List.of(
            Type.of("NEW", "", Category.BEVERAGE),
            Type.of("추천", "Recommend", Category.BEVERAGE),
            Type.of("에스프레소", "Espresso", Category.BEVERAGE));
    ProductType productType1 = new ProductType();
    ReflectionTestUtils.setField(productType1, "id", 1);
    ReflectionTestUtils.setField(productType1, "type", types.get(2)); // 에스프레소
    ReflectionTestUtils.setField(productType1, "product", products.get(0)); // 아메리카노
    ProductType productType2 = new ProductType();
    ReflectionTestUtils.setField(productType2, "id", 2);
    ReflectionTestUtils.setField(productType2, "type", types.get(1)); // 추천
    ReflectionTestUtils.setField(productType2, "product", products.get(0)); // 아메리카노
    ProductType productType3 = new ProductType();
    ReflectionTestUtils.setField(productType3, "id", 2);
    ReflectionTestUtils.setField(productType3, "type", types.get(0)); // NEW
    ReflectionTestUtils.setField(productType3, "product", products.get(1)); // 카페라떼
    ProductType productType4 = new ProductType();
    ReflectionTestUtils.setField(productType4, "id", 2);
    ReflectionTestUtils.setField(productType4, "type", types.get(2)); // 에스프레소
    ReflectionTestUtils.setField(productType4, "product", products.get(1)); // 카페라떼
    List<ProductType> productTypes =
        List.of(productType1, productType2, productType3, productType4);
  }

  @DisplayName("종류에 해당하는 상품들을 조회한다.")
  @Test
  void givenTypeId_whenFinding_thenReturnProductResponseDtos() {
    // given
    int typeId = 1; // 에스프레소
    given(productJpaRepository.findAllByTypeProductsTypeId(typeId))
        .willReturn(List.of(products.get(0), products.get(1)));

    // when
    List<ProductResponseDto> result = sut.findAllByTypeId(typeId);

    // then
    assertThat(result).hasSize(2);
    assertThat(result.get(0)).isEqualTo(ProductResponseDto.from(products.get(0)));
    then(productJpaRepository).should().findAllByTypeProductsTypeId(typeId);
  }

  @DisplayName("해당 종류에 해당하는 상품이 없으면 예외를 던진다.")
  @Test
  void givenNonExistsTypeId_whenFinding_thenThrowsIllegalArgumentException() {
    // given
    int nonExistsTypeId = 9999;

    // when
    Throwable t = catchThrowable(() -> sut.findAllByTypeId(nonExistsTypeId));

    // then
    assertThat(t)
        .isInstanceOf(ProductNotFoundInTypeException.class)
        .hasMessageContaining(PRODUCT_NOT_FOUND_IN_TYPE.getMessage());
    then(productJpaRepository).should().findAllByTypeProductsTypeId(nonExistsTypeId);
  }
}
