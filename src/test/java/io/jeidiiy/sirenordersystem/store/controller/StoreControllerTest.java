package io.jeidiiy.sirenordersystem.store.controller;

import static io.jeidiiy.sirenordersystem.jwt.filter.JwtAuthenticationFilter.BEARER_PREFIX;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeidiiy.sirenordersystem.config.SecurityConfig;
import io.jeidiiy.sirenordersystem.jwt.filter.JwtAuthenticationFilter;
import io.jeidiiy.sirenordersystem.jwt.model.JwtToken;
import io.jeidiiy.sirenordersystem.jwt.service.JwtAuthenticationEntryPoint;
import io.jeidiiy.sirenordersystem.jwt.service.JwtLogoutSuccessHandler;
import io.jeidiiy.sirenordersystem.jwt.service.JwtService;
import io.jeidiiy.sirenordersystem.security.WithMockCustomUser;
import io.jeidiiy.sirenordersystem.store.domain.Store;
import io.jeidiiy.sirenordersystem.store.domain.dto.StorePutRequestBody;
import io.jeidiiy.sirenordersystem.store.domain.dto.StoreResponseDto;
import io.jeidiiy.sirenordersystem.store.exception.StoreNotFoundException;
import io.jeidiiy.sirenordersystem.store.service.StoreService;
import io.jeidiiy.sirenordersystem.user.domain.Role;
import io.jeidiiy.sirenordersystem.user.domain.User;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[Controller] 매장 컨트롤러 테스트")
@Import({SecurityConfig.class, JwtService.class})
@WebMvcTest(StoreController.class)
class StoreControllerTest {
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper mapper;
  @Autowired JwtService jwtService;

  @MockitoBean StoreService storeService;
  @MockitoBean UserService userService;
  @MockitoBean JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  @MockitoBean JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

  private List<Store> stores;

  @BeforeEach
  void setup() {
    stores =
        List.of(
            Store.of("매장0", "주소0", "이미지0", "010-1234-5678", "09:00", "21:00", true),
            Store.of("매장1", "주소1", "이미지1", "010-1234-5678", "09:00", "21:00", true),
            Store.of("매장2", "주소2", "이미지2", "010-1234-5679", "09:00", "21:00", true));
  }

  @DisplayName("[GET] 매장 전체 목록 조회 -> 200 OK (정상)")
  @Test
  void givenNothing_whenRequesting_thenReturnStores() throws Exception {
    // given
    given(storeService.findStores())
        .willReturn(stores.stream().map(StoreResponseDto::from).toList());

    // when & then
    mvc.perform(get("/api/v1/stores")).andExpect(status().isOk());
    then(storeService).should().findStores();
  }

  @DisplayName("[GET] 주문할 특정 매장 조회 -> 200 OK (정상)")
  @Test
  void givenStoreId_whenRequesting_thenReturnsStore() throws Exception {
    // given
    Integer storeId = 1;
    Store store = stores.get(storeId);
    ReflectionTestUtils.setField(store, "id", storeId);
    given(storeService.findStoreById(storeId)).willReturn(StoreResponseDto.from(store));

    // when & then
    mvc.perform(get("/api/v1/stores/{storeId}", storeId)).andExpect(status().isOk());
    then(storeService).should().findStoreById(storeId);
  }

  @DisplayName("[GET] 존재하지 않는 특정 매장 조회 -> 404 NOT FOUND (실패)")
  @Test
  void givenNonExistsStoreId_whenRequesting_thenReturns404() throws Exception {
    // given
    Integer nonExistsStoreId = 9999;
    willThrow(new StoreNotFoundException(nonExistsStoreId))
        .given(storeService)
        .findStoreById(nonExistsStoreId);

    // when & then
    mvc.perform(get("/api/v1/stores/{nonExistsStoreId}", nonExistsStoreId))
        .andExpect(status().isNotFound());
    then(storeService).should().findStoreById(nonExistsStoreId);
  }

  @DisplayName("[PATCH] 매장 영업 상태 토글 -> 200 OK (정상)")
  @WithMockCustomUser(role = Role.ADMIN)
  @Test
  void givenAuthenticationUserAndStoreId_whenRequesting_thenReturn200() throws Exception {
    // given
    Integer storeId = 1;
    String username = "loginUsername";
    Store store = stores.get(storeId);
    User admin = User.builder().username(username).build();
    ReflectionTestUtils.setField(store, "id", storeId);
    ReflectionTestUtils.setField(store, "user", admin);
    JwtToken jwtToken = jwtService.generateJwtToken(username);
    willDoNothing()
        .given(storeService)
        .toggleStoreIsOpenByStoreIdAndLoginUserUsername(storeId, username);

    // when & then
    mvc.perform(
            patch("/api/v1/stores/{storeId}", storeId)
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + jwtToken.accessToken()))
        .andExpect(status().isOk());
    then(storeService).should().toggleStoreIsOpenByStoreIdAndLoginUserUsername(storeId, username);
  }

  @DisplayName("[PATCH] 로그인한 사용자가 해당 매장 관리자가 아닌 경우 -> 404 NOT FOUND (실패)")
  @WithMockCustomUser(role = Role.ADMIN)
  @Test
  void givenUnAuthenticationUserAndStoreId_whenRequesting_thenReturn404() throws Exception {
    // given
    Integer storeId = 1;
    String loginUsername = "loginUsername";
    String anotherUsername = "anotherUsername";
    Store store = stores.get(storeId);
    User admin = User.builder().username(anotherUsername).build();
    ReflectionTestUtils.setField(admin, "role", Role.ADMIN);
    ReflectionTestUtils.setField(store, "id", storeId);
    ReflectionTestUtils.setField(store, "user", admin);
    JwtToken jwtToken = jwtService.generateJwtToken(loginUsername);

    willThrow(new StoreNotFoundException(storeId, loginUsername))
        .given(storeService)
        .toggleStoreIsOpenByStoreIdAndLoginUserUsername(storeId, loginUsername);

    // when & then
    mvc.perform(
            patch("/api/v1/stores/{storeId}", storeId)
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + jwtToken.accessToken()))
        .andExpect(status().isNotFound());
    then(storeService)
        .should()
        .toggleStoreIsOpenByStoreIdAndLoginUserUsername(storeId, loginUsername);
  }

  @DisplayName("[PUT] 매장 관리자가 매장 정보 수정 -> 200 OK [성공]")
  @WithMockCustomUser(role = Role.ADMIN)
  @Test
  void givenAuthenticationUserAndPutRequestBodyAndStoreId_whenUpdating_thenReturns200()
      throws Exception {
    // given
    Integer storeId = 1;
    String username = "loginUsername";
    Store store = stores.get(storeId);
    User admin = User.builder().username(username).build();
    ReflectionTestUtils.setField(store, "id", storeId);
    ReflectionTestUtils.setField(store, "user", admin);
    JwtToken jwtToken = jwtService.generateJwtToken(username);
    StorePutRequestBody requestBody =
        StorePutRequestBody.builder()
            .name("매장3")
            .address("위치3")
            .imageUrl("이미지3")
            .contactNumber("012-123-1234")
            .openAt("06:00")
            .closeAt("18:00")
            .isOpen(true)
            .build();
    willDoNothing()
        .given(storeService)
        .updateStoreByStoreIdAndLoginUserUsername(requestBody, storeId, username);

    // when & then
    mvc.perform(
            put("/api/v1/stores/{storeId}", storeId)
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + jwtToken.accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestBody)))
        .andExpect(status().isOk());
    then(storeService)
        .should()
        .updateStoreByStoreIdAndLoginUserUsername(requestBody, storeId, username);
  }

  @DisplayName("[PUT] 매장 관리자가 아닌 사람이 매장 정보 수정 -> 404 NOT FOUND [실패]")
  @WithMockCustomUser(role = Role.ADMIN)
  @Test
  void givenUnAuthenticationUserAndPutRequestBodyAndStoreId_whenUpdating_thenReturns404()
      throws Exception {
    // given
    Integer storeId = 1;
    String username = "loginUsername";
    String anotherUsername = "anotherUsername";
    Store store = stores.get(storeId);
    User admin = User.builder().username(anotherUsername).build();
    ReflectionTestUtils.setField(store, "id", storeId);
    ReflectionTestUtils.setField(store, "user", admin);
    JwtToken jwtToken = jwtService.generateJwtToken(username);
    StorePutRequestBody requestBody =
        StorePutRequestBody.builder()
            .name("매장3")
            .address("위치3")
            .imageUrl("이미지3")
            .contactNumber("012-123-1234")
            .openAt("06:00")
            .closeAt("18:00")
            .isOpen(true)
            .build();
    willThrow(new StoreNotFoundException(storeId, username))
        .given(storeService)
        .updateStoreByStoreIdAndLoginUserUsername(requestBody, storeId, username);

    // when & then
    mvc.perform(
            put("/api/v1/stores/{storeId}", storeId)
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + jwtToken.accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestBody)))
        .andExpect(status().isNotFound());
    then(storeService)
        .should()
        .updateStoreByStoreIdAndLoginUserUsername(requestBody, storeId, username);
  }
}
