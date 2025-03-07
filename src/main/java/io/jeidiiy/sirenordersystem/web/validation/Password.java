package io.jeidiiy.sirenordersystem.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class) // 검증 로직을 수행할 Validator 클래스 지정
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
  String message() default "비밀번호는 8~15자의 영문, 숫자, 특수문자만 가능합니다.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
