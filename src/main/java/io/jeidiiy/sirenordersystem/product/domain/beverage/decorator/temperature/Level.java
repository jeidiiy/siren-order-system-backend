package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.temperature;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Level {
  WARM,
  HOT;

  @JsonCreator
  public static Level forValue(String value) {
    return Level.valueOf(value.toUpperCase());
  }

  @JsonValue
  public String toJson() {
    return name().toLowerCase();
  }
}
