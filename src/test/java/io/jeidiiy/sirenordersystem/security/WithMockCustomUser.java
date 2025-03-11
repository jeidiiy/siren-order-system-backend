package io.jeidiiy.sirenordersystem.security;

import io.jeidiiy.sirenordersystem.user.domain.Role;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
  String username() default "loginUsername";

  String password() default "password11!!";

  Role role() default Role.CUSTOMER;
}
