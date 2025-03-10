package io.jeidiiy.sirenordersystem.user.service;

import io.jeidiiy.sirenordersystem.jwt.model.JwtToken;
import io.jeidiiy.sirenordersystem.jwt.model.RefreshToken;
import io.jeidiiy.sirenordersystem.jwt.service.JwtService;
import io.jeidiiy.sirenordersystem.jwt.service.RefreshTokenService;
import io.jeidiiy.sirenordersystem.user.domain.User;
import io.jeidiiy.sirenordersystem.user.domain.dto.AuthenticationUser;
import io.jeidiiy.sirenordersystem.user.domain.dto.UserLoginRequestBody;
import io.jeidiiy.sirenordersystem.user.domain.dto.UserPatchRequestBody;
import io.jeidiiy.sirenordersystem.user.domain.dto.UserPostRequestBody;
import io.jeidiiy.sirenordersystem.user.exception.UserAlreadyExistsException;
import io.jeidiiy.sirenordersystem.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService implements UserDetailsService {

  private final UserJpaRepository userJpaRepository;
  private final JwtService jwtService;
  private final RefreshTokenService refreshTokenService;
  private final PasswordEncoder passwordEncoder;

  public void signUp(UserPostRequestBody userPostRequestBody) {
    if (userJpaRepository.findByUsername(userPostRequestBody.getUsername()).isPresent()) {
      throw new UserAlreadyExistsException("이미 존재하는 사용자입니다.");
    }

    User newUser = userPostRequestBody.toEntity();
    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    userJpaRepository.save(newUser);
  }

  public void updateUserByUsername(String username, UserPatchRequestBody userPatchRequestBody) {
    User user = userJpaRepository.findByUsername(username).orElseThrow();

    user.setUsername(userPatchRequestBody.getUsername());
    user.setRealname(userPatchRequestBody.getRealname());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setNickname(userPatchRequestBody.getNickname());

    userJpaRepository.save(user);
  }

  public void deleteUserByUsername(String username) {
    User user = getUserByUsername(username);
    userJpaRepository.delete(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userJpaRepository
        .findByUsername(username)
        .map(
            user ->
                AuthenticationUser.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .build())
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }

  public JwtToken login(UserLoginRequestBody userLoginRequestBody) {
    var user = getUserByUsername(userLoginRequestBody.username());

    if (!passwordEncoder.matches(userLoginRequestBody.password(), user.getPassword())) {
      throw new UsernameNotFoundException(userLoginRequestBody.username());
    }

    // 리프레시 토큰이 없는 경우 액세스 토큰과 리프레시 토큰을 생성하여 저장
    Optional<RefreshToken> optionalRefreshToken = refreshTokenService.findByUser(user);
    if (optionalRefreshToken.isEmpty()) {
      var jwtToken = jwtService.generateJwtToken(user.getUsername());
      refreshTokenService.save(
          RefreshToken.builder()
              .user(user)
              .token(jwtToken.refreshToken())
              .expiryDate(jwtService.getExpiration(jwtToken.refreshToken()).toInstant())
              .build());
      return jwtToken;
    } else {
      // 리프레시 토큰이 있는 경우 액세스 토큰만 생성하여 반환
      String accessToken = jwtService.generateAccessToken(new Date(), user.getUsername());
      String refreshToken = optionalRefreshToken.get().getToken();
      return JwtToken.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }
  }

  public User getUserByUsername(String username) {
    return userJpaRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }
}
