package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.ice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Amount {
  SOME,
  ENOUGH,
  MUCH;

  @JsonCreator
  public static Amount forValue(String value) {
    return Amount.valueOf(value.toUpperCase());
  }

  @JsonValue
  public String toJson() {
    return name().toLowerCase();
  }
}
