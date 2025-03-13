package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.temperature;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;

public class Temperature extends BeverageDecorator {
  private final Level level;

  public Temperature(Beverage beverage) {
    super(beverage);
    this.level = Level.WARM;
  }

  public Temperature(Beverage beverage, Level level) {
    super(beverage);
    this.level = level;
  }
}
