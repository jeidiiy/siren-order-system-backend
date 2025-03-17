package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.temperature;

import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;

public class Temperature extends BeverageDecorator {
  private final Level level;

  public Temperature(BeverageDto beverageDto) {
    super(beverageDto);
    this.level = Level.WARM;
  }

  public Temperature(BeverageDto beverageDto, Level level) {
    super(beverageDto);
    this.level = level;
  }

  @Override
  public String getOptionKey() {
    return "temperature";
  }

  @Override
  public Object getOptionValue() {
    return level;
  }
}
