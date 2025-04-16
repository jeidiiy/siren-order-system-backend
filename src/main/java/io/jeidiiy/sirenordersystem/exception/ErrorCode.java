package io.jeidiiy.sirenordersystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  // [도메인]_[HTTP 상태코드]_[설명] 형식

  // 주문 관련
  ORDER_PRODUCT_IS_EMPTY("ORD-4001", HttpStatus.BAD_REQUEST, "주문된 상품이 없습니다"),
  ORDER_NOT_FOUND("ORD-4041", HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),

  // 장바구니 관련
  CART_PRODUCT_ID_IS_MISSING("CRT-4001", HttpStatus.BAD_REQUEST, "장바구니에 담을 상품 ID는 필수값입니다."),
  CART_NOT_FOUND("CRT-4041", HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."),

  // JWT 관련
  JWT_UNSUPPORTED("JWT-4001", HttpStatus.BAD_REQUEST, "지원하지 않는 JWT 형식입니다."),
  JWT_MALFORMED("JWT-4002", HttpStatus.BAD_REQUEST, "잘못된 JWT 구조입니다."),
  JWT_SIGNATURE("JWT-4003", HttpStatus.BAD_REQUEST, "잘못된 서명입니다."),
  JWT_IS_EXPIRED("JWT-4011", HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
  JWT_USERNAME_NOT_FOUND("JWT-4041", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

  // 리프레시 토큰 관련
  REFRESH_TOKEN_VALID_ACCESS_TOKEN(
      "RFT-4001", HttpStatus.BAD_REQUEST, "액세스 토큰이 유효합니다. 새로운 액세스 토큰을 발급할 수 없습니다."),
  REFRESH_TOKEN_IS_EXPIRED("RFT-4011", HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다. 다시 로그인해 주세요."),
  REFRESH_TOKEN_NOT_FOUND("RFT-4042", HttpStatus.NOT_FOUND, "리프레시 토큰을 찾을 수 없습니다."),
  REFRESH_TOKEN_NOT_FOUND_IN_COOKIE("RFT-4043", HttpStatus.NOT_FOUND, "쿠키에 리프레시 토큰이 없습니다."),

  // 사용자 관련
  USER_NOT_FOUND("USR-4041", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
  USER_FORBIDDEN("USR-4031", HttpStatus.FORBIDDEN, "권한이 없습니다"),
  USER_ALREADY_EXIST("USR-4091", HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
  USER_CONFLICT_PASSWORD("USR-4092", HttpStatus.CONFLICT, "비밀번호가 일치하지 않습니다"),

  // 매장 관련
  STORE_NOT_FOUND("STO-4041", HttpStatus.NOT_FOUND, "매장을 찾을 수 없습니다."),
  STORE_NOT_FOUND_WITH_USERNAME("STO-4042", HttpStatus.NOT_FOUND, "사용자가 관리 중인 매장이 아닙니다."),

  // 상품 관련
  PRODUCT_NOT_FOUND("PRD-4041", HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
  PRODUCT_NOT_FOUND_IN_TYPE("PRD-4042", HttpStatus.NOT_FOUND, "주어진 종류에 포함되는 상품이 없습니다."),

  // 종류 관련
  TYPE_NOT_FOUND("TYP-4041", HttpStatus.NOT_FOUND, "종류를 찾을 수 없습니다."),

  // @Valid 규칙 위반
  INVALID_INPUT_VALUE("VAL-4001", HttpStatus.BAD_REQUEST, "입력값이 잘못되었습니다."),

  // 일반적인 예외
  ILLEGAL_ARGUMENT("CMM-4001", HttpStatus.BAD_REQUEST, "입력값이 잘못되었습니다.");

  private final String code;
  private final HttpStatus status;
  private final String message;

  ErrorCode(String code, HttpStatus status, String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }
}
