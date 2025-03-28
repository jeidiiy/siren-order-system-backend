package io.jeidiiy.sirenordersystem.jwt.filter;

import io.jeidiiy.sirenordersystem.jwt.exception.IlligalSignatureException;
import io.jeidiiy.sirenordersystem.jwt.exception.SecurityMalformedJwtException;
import io.jeidiiy.sirenordersystem.jwt.exception.SecurityUnsupportedJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthExceptionFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    Exception ex = (Exception) request.getAttribute("authException");
    if (ex != null) {
      if (ex instanceof ExpiredJwtException) {
        throw new CredentialsExpiredException("만료된 토큰입니다");
      } else if (ex instanceof UnsupportedJwtException) {
        throw new SecurityUnsupportedJwtException("지원하지 않는 JWT 형식입니다");
      } else if (ex instanceof MalformedJwtException) {
        throw new SecurityMalformedJwtException("잘못된 JWT 구조입니다");
      } else if (ex instanceof SignatureException) {
        throw new IlligalSignatureException("잘못된 서명입니다");
      } else if (ex instanceof UsernameNotFoundException) {
        throw new UsernameNotFoundException(ex.getMessage());
      }
    }

    filterChain.doFilter(request, response);
  }
}
