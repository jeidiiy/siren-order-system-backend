package io.jeidiiy.sirenordersystem.type.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeidiiy.sirenordersystem.config.SecurityConfig;
import io.jeidiiy.sirenordersystem.jwt.filter.JwtAuthenticationFilter;
import io.jeidiiy.sirenordersystem.jwt.service.JwtAuthenticationEntryPoint;
import io.jeidiiy.sirenordersystem.jwt.service.JwtLogoutSuccessHandler;
import io.jeidiiy.sirenordersystem.jwt.service.JwtService;
import io.jeidiiy.sirenordersystem.type.domain.Category;
import io.jeidiiy.sirenordersystem.type.domain.Type;
import io.jeidiiy.sirenordersystem.type.domain.dto.TypeResponseDto;
import io.jeidiiy.sirenordersystem.type.service.TypeService;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[Controller] 종류 컨트롤러 테스트")
@Import({SecurityConfig.class, JwtAuthenticationFilter.class, JwtService.class})
@WebMvcTest(TypeController.class)
class TypeControllerTest {
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper mapper;
  @Autowired JwtService jwtService;

  @MockitoBean TypeService typeService;
  @MockitoBean UserService userService;
  @MockitoBean JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  @MockitoBean JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

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

  @DisplayName("[GET] 주어진 카테고리로 종류 조회 -> 200 OK [성공]")
  @Test
  void givenCategory_whenRequesting_thenRespond200() throws Exception {
    // given
    Category category = Category.BEVERAGE;

    given(typeService.findTypeByCategory(category))
        .willReturn(
            List.of(
                TypeResponseDto.from(types.get(0)),
                TypeResponseDto.from(types.get(1)),
                TypeResponseDto.from(types.get(2))));

    // when & then
    mvc.perform(get("/api/v1/types/{category}", category.name().toLowerCase())).andExpect(status().isOk());
    then(typeService).should().findTypeByCategory(category);
  }

  @DisplayName("[GET] 없는 카테고리로 종류 조회 -> 400 BAD REQUEST [실패]")
  @Test
  void givenNonExistsCategory_whenRequesting_thenRespond400() throws Exception {
    // given

    // when & then
    mvc.perform(get("/api/v1/types/{category}", "NonExistsCategory")).andExpect(status().isBadRequest());
    then(typeService).shouldHaveNoInteractions();
  }
}
