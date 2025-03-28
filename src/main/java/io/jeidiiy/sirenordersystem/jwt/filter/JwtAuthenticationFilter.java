package io.jeidiiy.sirenordersystem.jwt.filter;

import io.jeidiiy.sirenordersystem.jwt.exception.IlligalSignatureException;
import io.jeidiiy.sirenordersystem.jwt.service.JwtService;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  public static final String BEARER_PREFIX = "Bearer ";
  private final JwtService jwtService;
  private final UserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    var securityContext = SecurityContextHolder.getContext();

    if (!ObjectUtils.isEmpty(authorization)
        && authorization.startsWith(BEARER_PREFIX)
        && securityContext.getAuthentication() == null
        && !request.getRequestURI().equals("/api/v1/refresh-token")) {
      var accessToken = authorization.substring(BEARER_PREFIX.length()).trim();
      String username;
      try {
        username = jwtService.getUsername(accessToken);
        var userDetails = userService.loadUserByUsername(username);
        var authenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        securityContext.setAuthentication(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      } catch (JwtException | UsernameNotFoundException ex) {
        // 예외를 request 에 저장 후 나중에 JwtAuthExceptionFilter 에서 처리
        request.setAttribute("authException", ex);
      }
    }
    filterChain.doFilter(request, response);
  }
}
