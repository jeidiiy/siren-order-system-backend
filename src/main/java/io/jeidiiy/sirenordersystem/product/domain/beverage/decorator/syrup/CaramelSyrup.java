package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.syrup;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;

public class CaramelSyrup extends Syrup {
  private final int number;

  public CaramelSyrup(Beverage beverage, int number) {
    super(beverage);
    this.number = number;
  }

  @Override
  public Integer getBasePrice() {
    return super.getBasePrice() + 800;
  }
}
