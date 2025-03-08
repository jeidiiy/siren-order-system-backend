package io.jeidiiy.sirenordersystem.config;

import static org.springframework.http.HttpMethod.POST;

import io.jeidiiy.sirenordersystem.jwt.filter.JwtAuthenticationFilter;
import io.jeidiiy.sirenordersystem.jwt.service.JwtLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(
            auth ->
                auth.requestMatchers(POST, "/api/v1/users/authenticate") // 로그인 API
                    .permitAll()
                    .requestMatchers(POST, "/api/v1/refresh-token") // Access Token 재발급 API
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(CsrfConfigurer::disable)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .logout(
            logout -> logout.logoutSuccessHandler(jwtLogoutSuccessHandler))
        .build();
  }
}
