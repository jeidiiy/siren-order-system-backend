package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.syrup;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;

public abstract class Syrup extends BeverageDecorator {
  public Syrup(Beverage beverage) {
    super(beverage);
  }
}
