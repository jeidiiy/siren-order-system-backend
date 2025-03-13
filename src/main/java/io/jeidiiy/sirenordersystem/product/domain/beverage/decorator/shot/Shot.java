package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.shot;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;

public class Shot extends BeverageDecorator {
  private static final Integer DEFAULT_SHOTS = 2;
  private final Integer number;

  public Shot(Beverage beverage, Integer number) {
    super(beverage);
    this.number = number;
  }

  @Override
  public Integer getBasePrice() {
    if (number > DEFAULT_SHOTS) {
      return super.getBasePrice() * (number - DEFAULT_SHOTS);
    }
    return super.getBasePrice();
  }
}
