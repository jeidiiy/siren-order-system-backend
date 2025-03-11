package io.jeidiiy.sirenordersystem.security;

import io.jeidiiy.sirenordersystem.user.domain.dto.AuthenticationUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory
    implements WithSecurityContextFactory<WithMockCustomUser> {
  @Override
  public SecurityContext createSecurityContext(WithMockCustomUser user) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();

    AuthenticationUser authenticationUser =
        AuthenticationUser.builder()
            .username(user.username())
            .password(user.password())
            .role(user.role())
            .build();

    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(
            authenticationUser, null, authenticationUser.getAuthorities());

    context.setAuthentication(token);

    return context;
  }
}
