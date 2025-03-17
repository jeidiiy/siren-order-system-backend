package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.syrup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SyrupType {
  CARAMEL,
  HAZELNUT,
  VANILLA;

  @JsonCreator
  public static SyrupType forValue(String value) {
    return SyrupType.valueOf(value.toUpperCase());
  }

  @JsonValue
  public String toJson() {
    return name().toLowerCase();
  }
}
