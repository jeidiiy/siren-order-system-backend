package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.cup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CupSize {
  SHORT,
  TALL,
  GRANDE,
  VENTI;

  @JsonCreator
  public static CupSize forValue(String value) {
    return CupSize.valueOf(value.toUpperCase());
  }

  @JsonValue
  public String toJson() {
    return name().toLowerCase();
  }
}
