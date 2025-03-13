package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.water;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;

public class Water extends BeverageDecorator {
  private final Amount amount;

  public Water(Beverage beverage) {
    super(beverage);
    amount = Amount.ENOUGH;
  }

  public Water(Beverage beverage, Amount amount) {
    super(beverage);
    this.amount = amount;
  }
}
