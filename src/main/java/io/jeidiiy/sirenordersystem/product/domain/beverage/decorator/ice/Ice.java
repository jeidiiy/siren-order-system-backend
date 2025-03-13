package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.ice;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;

public class Ice extends BeverageDecorator {
  private final Amount amount;

  public Ice(Beverage beverage) {
    super(beverage);
    this.amount = Amount.ENOUGH;
  }

  public Ice(Beverage beverage, Amount amount) {
    super(beverage);
    this.amount = amount;
  }
}
