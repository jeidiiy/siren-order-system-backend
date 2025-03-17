package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BeanType {
  SIGNATURE,
  BLONDE,
  DECAF,
  HALF_DECAF;

  @JsonCreator
  public static BeanType forValue(String value) {
    return BeanType.valueOf(value.toUpperCase());
  }

  @JsonValue
  public String toJson() {
    return name().toLowerCase();
  }
}
