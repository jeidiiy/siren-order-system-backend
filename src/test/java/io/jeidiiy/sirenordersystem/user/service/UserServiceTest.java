package io.jeidiiy.sirenordersystem.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import io.jeidiiy.sirenordersystem.user.domain.dto.UserPostRequestBody;
import io.jeidiiy.sirenordersystem.user.exception.UserAlreadyExistsException;
import io.jeidiiy.sirenordersystem.user.repository.UserJpaRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("[Service] 사용자 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @InjectMocks UserService sut;

  @Mock UserJpaRepository userJpaRepository;
  @Mock PasswordEncoder passwordEncoder;

  @DisplayName("존재하지 않는 회원 정보가 주어지면 , 회원을 추가한다.")
  @Test
  void givenNonExistsUser_whenInserting_thenCreatesUser() {
    // given
    UserPostRequestBody userPostRequestBody =
        UserPostRequestBody.builder()
            .username("test")
            .realname("테스트찐이름")
            .password("password11!!")
            .nickname("테스트닉네임")
            .build();
    given(userJpaRepository.findByUsername(userPostRequestBody.getUsername()))
        .willReturn(Optional.empty());
    given(passwordEncoder.encode(userPostRequestBody.getPassword())).willReturn("password11!!");

    // when
    sut.signUp(userPostRequestBody);

    // then
    then(userJpaRepository).should().findByUsername(userPostRequestBody.getUsername());
    then(userJpaRepository).should().save(userPostRequestBody.toEntity());
  }

  @DisplayName("존재하는 회원 정보가 주어지면, 예외를 던진다.")
  @Test
  void givenExistsUser_whenInserting_thenThrowsException() {
    // given
    UserPostRequestBody userPostRequestBody =
        UserPostRequestBody.builder()
            .username("test")
            .realname("테스트찐이름")
            .password("password11!!")
            .nickname("테스트닉네임")
            .build();
    given(userJpaRepository.findByUsername(userPostRequestBody.getUsername()))
        .willReturn(Optional.of(userPostRequestBody.toEntity()));

    // when
    Throwable t = catchThrowable(() -> sut.signUp(userPostRequestBody));

    // then
    assertThat(t)
        .isInstanceOf(UserAlreadyExistsException.class)
        .hasMessageContaining("이미 존재하는 사용자입니다.");
    then(userJpaRepository).should().findByUsername(userPostRequestBody.getUsername());
  }
}
