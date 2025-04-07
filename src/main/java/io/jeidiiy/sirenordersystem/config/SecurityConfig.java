package io.jeidiiy.sirenordersystem.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import io.jeidiiy.sirenordersystem.jwt.filter.JwtAuthExceptionFilter;
import io.jeidiiy.sirenordersystem.jwt.filter.JwtAuthenticationFilter;
import io.jeidiiy.sirenordersystem.jwt.service.JwtAuthenticationEntryPoint;
import io.jeidiiy.sirenordersystem.jwt.service.JwtLogoutSuccessHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtAuthExceptionFilter jwtAuthExceptionFilter;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(
            auth ->
                auth.requestMatchers(POST, "/api/v1/users") // 회원가입 API
                    .permitAll()
                    .requestMatchers(POST, "/api/v1/users/authenticate") // 로그인 API
                    .permitAll()
                    .requestMatchers(POST, "/api/v1/refresh-token") // Access Token 재발급 API
                    .permitAll()
                    .requestMatchers(GET, "/api/v1/stores/**") // 매장 목록 조회 API
                    .permitAll()
                    .requestMatchers(GET, "/api/v1/types/**") // 종류 목록 조회 API
                    .permitAll()
                    .requestMatchers(GET, "/api/v1/products/**") // 상품 목록 조회 API
                    .permitAll()
                    .requestMatchers(
                        "/swagger-ui/**") // TODO: 스웨거 페이지. 개발 편의성을 위해 오픈해둠. 배포 시 제거해야 함.
                    .permitAll()
                    .requestMatchers("/v3/api-docs/**") // TODO: 스웨거에서 요청하는 URL. 이도 위 옵션과 동일.
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(CsrfConfigurer::disable)
        .cors(Customizer.withDefaults())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtAuthExceptionFilter, AuthorizationFilter.class)
        .exceptionHandling(
            exceptionHandler ->
                exceptionHandler.authenticationEntryPoint(jwtAuthenticationEntryPoint))
        .logout(
            logout ->
                logout.logoutUrl("/api/v1/logout").logoutSuccessHandler(jwtLogoutSuccessHandler))
        .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000"));
    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
    corsConfiguration.setAllowedHeaders(List.of("*"));
    corsConfiguration.addExposedHeader("Authorization");
    corsConfiguration.setAllowCredentials(true);
    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/v1/**", corsConfiguration);
    return source;
  }
}
