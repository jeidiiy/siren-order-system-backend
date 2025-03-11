package io.jeidiiy.sirenordersystem.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeidiiy.sirenordersystem.config.SecurityConfig;
import io.jeidiiy.sirenordersystem.jwt.filter.JwtAuthenticationFilter;
import io.jeidiiy.sirenordersystem.jwt.model.JwtToken;
import io.jeidiiy.sirenordersystem.jwt.service.JwtAuthenticationEntryPoint;
import io.jeidiiy.sirenordersystem.jwt.service.JwtLogoutSuccessHandler;
import io.jeidiiy.sirenordersystem.jwt.service.JwtService;
import io.jeidiiy.sirenordersystem.security.WithMockCustomUser;
import io.jeidiiy.sirenordersystem.user.domain.dto.UserPatchRequestBody;
import io.jeidiiy.sirenordersystem.user.domain.dto.UserPostRequestBody;
import io.jeidiiy.sirenordersystem.user.exception.UserAlreadyExistsException;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[Controller] 사용자 컨트롤러 테스트")
@Import({SecurityConfig.class, JwtAuthenticationFilter.class, JwtService.class})
@WebMvcTest(UserController.class)
class UserControllerTest {
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper mapper;
  @Autowired JwtService jwtService;

  @MockitoBean UserService userService;
  @MockitoBean JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  @MockitoBean JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

  @DisplayName("[GET] 로그인한 유저가 자기 정보 조회 -> 200 OK (정상)")
  @WithMockCustomUser
  @Test
  void givenAuthenticationUserAndSameUsername_whenRequesting_thenRespondsUser() throws Exception {
    // given
    String username = "loginUsername";
    JwtToken jwtToken = jwtService.generateJwtToken(username);

    // when & then
    mvc.perform(
            get("/api/v1/users/{username}", username)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken.accessToken()))
        .andExpect(status().isOk());
    then(userService).should().getUserByUsername(username);
  }

  @DisplayName("[GET] 로그인한 유저가 다른 사람 정보 조회 -> 403 Forbidden (실패)")
  @WithMockCustomUser
  @Test
  void givenAuthenticationUserAndDifferentUsername_whenRequesting_thenResponds403()
      throws Exception {
    // given
    String anotherUsername = "anotherUser";
    JwtToken jwtToken = jwtService.generateJwtToken(anotherUsername);

    // when & then
    mvc.perform(
            get("/api/v1/users/{username}", anotherUsername)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken.accessToken()))
        .andExpect(status().isForbidden());
    then(userService).shouldHaveNoInteractions();
  }

  @DisplayName("[POST] 존재하지 않는 사용자 이름으로 회원가입 시도 -> 201 Created (정상)")
  @Test
  void givenNonExistsUserRequest_whenCreating_thenResponds201() throws Exception {
    // given
    UserPostRequestBody userPostRequestBody =
        UserPostRequestBody.builder()
            .username("test")
            .realname("테스트찐이름")
            .password("password11!!")
            .nickname("테스트닉네임")
            .build();
    willDoNothing().given(userService).signUp(userPostRequestBody);

    // when & then
    mvc.perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userPostRequestBody)))
        .andExpect(status().isCreated());
    then(userService).should().signUp(userPostRequestBody);
  }

  @DisplayName("[POST] 이미 존재하는 사용자 ID로 회원가입 시도 -> 409 Conflict (실패)")
  @Test
  void givenExistsUserRequest_whenCreating_thenResponds409() throws Exception {
    // given
    UserPostRequestBody userPostRequestBody =
        UserPostRequestBody.builder()
            .username("test")
            .realname("테스트찐이름")
            .password("password11!!")
            .nickname("테스트닉네임")
            .build();

    willThrow(new UserAlreadyExistsException("이미 존재하는 사용자입니다."))
        .given(userService)
        .signUp(any(UserPostRequestBody.class));

    // when & then
    mvc.perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userPostRequestBody)))
        .andExpect(status().isConflict());
    then(userService).should().signUp(userPostRequestBody);
  }

  @DisplayName("[POST] 회원가입 시 username 에 대한 유효성 검사 실패 -> 400 (실패)")
  @CsvSource({
    "''", "'    '", "test1", "1test", "te1st", "test#", "#test", "te#st", "테스트", "test테스트"
  })
  @ParameterizedTest
  void givenInvalidUsername_whenSignup_thenResponds400(String username) throws Exception {
    // given
    UserPostRequestBody userPostRequestBody =
        UserPostRequestBody.builder()
            .username(username)
            .password("password12!!")
            .realname("테스트찐이름")
            .build();

    // when & then
    mvc.perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userPostRequestBody)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(Matchers.containsString("아이디는 영문만 가능합니다.")));
    then(userService).shouldHaveNoInteractions();
  }

  @DisplayName("[POST] 회원가입 시 password 에 대한 유효성 검사 실패 -> 400 (실패)")
  @CsvSource({
    "''",
    "'    '",
    "abcdefg", // short
    "abcdefgh", // only english
    "12345678", // only number
    "!@#$%^&*", // only special character
    "abcd1234", // no special character
    "abcd!@#$", // no number
    "1234!@#$", // no english
    "abcdefgh1234!@#$" // long
  })
  @ParameterizedTest
  void givenInvalidPassword_whenSignup_thenResponds400(String password) throws Exception {
    // given
    UserPostRequestBody userPostRequestBody =
        UserPostRequestBody.builder()
            .username("username")
            .password(password)
            .realname("테스트찐이름")
            .build();

    // when & then
    mvc.perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userPostRequestBody)))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.message")
                .value(Matchers.containsString("비밀번호는 8~15자의 영문, 숫자, 특수문자만 가능합니다.")));
    then(userService).shouldHaveNoInteractions();
  }

  @DisplayName("[POST] 회원가입 시 realname 에 대한 유효성 검사 실패 -> 400 (실패)")
  @CsvSource({"''", "'   '", "1234", "david", "!@#$", "가나다라마바사"})
  @ParameterizedTest
  void givenInvalidRealname_whenSignup_thenResponds400(String realname) throws Exception {
    // given
    UserPostRequestBody userPostRequestBody =
        UserPostRequestBody.builder()
            .username("username")
            .realname(realname)
            .password("password123!@#")
            .build();

    // when & then
    mvc.perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userPostRequestBody)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(Matchers.containsString("이름은 1~6자의 한글만 가능합니다.")));
    then(userService).shouldHaveNoInteractions();
  }

  @DisplayName("[POST] 회원가입 시 nickname 에 대한 유효성 검사 실패 -> 400 (실패)")
  @CsvSource({"''", "'   '", "1234", "david", "!@#$", "가나다라마바사"})
  @ParameterizedTest
  void givenInvalidNickname_whenSignup_thenResponds400(String nickname) throws Exception {
    // given
    UserPostRequestBody userPostRequestBody =
        UserPostRequestBody.builder()
            .username("username")
            .password("password123!@#")
            .realname("테스트찐이름")
            .nickname(nickname)
            .build();

    // when & then
    mvc.perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userPostRequestBody)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(Matchers.containsString("닉네임은 1~6자의 한글만 가능합니다.")));
    then(userService).shouldHaveNoInteractions();
  }

  @DisplayName("[PATCH] 로그인한 유저가 자기 정보 수정 -> 200 OK (정상)")
  @WithMockCustomUser
  @Test
  void givenAuthenticationUserAndSameUsername_whenUpdating_thenResponds200() throws Exception {
    // given
    String username = "loginUsername";
    JwtToken jwtToken = jwtService.generateJwtToken(username);
    UserPatchRequestBody userPatchRequestBody =
        UserPatchRequestBody.builder()
            .nickname("테스트닉네임")
            .password("password11!!")
            .realname("테스트찐이름")
            .username(username)
            .build();

    // when & then
    mvc.perform(
            patch("/api/v1/users/{username}", username)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken.accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userPatchRequestBody)))
        .andExpect(status().isOk());
    then(userService).should().updateUserByUsername(username, userPatchRequestBody);
  }

  @DisplayName("[PATCH] 로그인한 유저가 다른 사람 정보 수정 -> 403 Forbidden (실패)")
  @WithMockCustomUser
  @Test
  void givenAuthenticationUserAndAnotherUsername_whenUpdating_thenResponds403() throws Exception {
    // given
    String anotherUsername = "anotherUsername";
    JwtToken jwtToken = jwtService.generateJwtToken(anotherUsername);
    UserPatchRequestBody userPatchRequestBody =
        UserPatchRequestBody.builder()
            .nickname("테스트닉네임")
            .password("password11!!")
            .realname("테스트찐이름")
            .username(anotherUsername)
            .build();

    // when & then
    mvc.perform(
            delete("/api/v1/users/{username}", anotherUsername)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken.accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userPatchRequestBody)))
        .andExpect(status().isForbidden());
    then(userService).shouldHaveNoInteractions();
  }

  @DisplayName("[DELETE] 로그인한 유저가 회원 탈퇴 -> 200 OK (정상)")
  @WithMockCustomUser
  @Test
  void givenAuthenticationUserAndSameUsername_whenDeleting_thenResponds200() throws Exception {
    // given
    String username = "loginUsername";
    JwtToken jwtToken = jwtService.generateJwtToken(username);

    // when & then
    mvc.perform(
            delete("/api/v1/users/{username}", username)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken.accessToken()))
        .andExpect(status().isOk());
    then(userService).should().deleteUserByUsername(username);
  }

  @DisplayName("[DELETE] 로그인한 유저가 다른 사람 정보 삭제 -> 403 Forbidden (실패)")
  @WithMockUser("loginUsername")
  @Test
  void givenAuthenticationUserAndAnotherUsername_whenDeleting_thenResponds403() throws Exception {
    // given
    String anotherUsername = "anotherUsername";
    JwtToken jwtToken = jwtService.generateJwtToken(anotherUsername);

    // when & then
    mvc.perform(
            delete("/api/v1/users/{username}", anotherUsername)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken.accessToken())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
    then(userService).shouldHaveNoInteractions();
  }
}
