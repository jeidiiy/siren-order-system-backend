package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.whippedcream;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;

public class WhippedCream extends BeverageDecorator {
  private final Amount amount;

  public WhippedCream(Beverage beverage) {
    super(beverage);
    this.amount = Amount.ENOUGH;
  }

  public WhippedCream(Beverage beverage, Amount amount) {
    super(beverage);
    this.amount = amount;
  }
}
