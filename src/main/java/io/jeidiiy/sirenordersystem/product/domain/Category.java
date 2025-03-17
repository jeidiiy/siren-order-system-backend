package io.jeidiiy.sirenordersystem.product.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
  BEVERAGE,
  FOOD,
  MERCHANDISE;

  @JsonCreator
  public static Category fromString(String value) {
    return Category.valueOf(value.toUpperCase());
  }

  @JsonValue
  public String toJson() {
    return name().toLowerCase();
  }
}
