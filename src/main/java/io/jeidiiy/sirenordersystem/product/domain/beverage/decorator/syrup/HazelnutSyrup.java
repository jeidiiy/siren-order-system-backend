package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.syrup;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;

public class HazelnutSyrup extends Syrup {
  private final int number;

  public HazelnutSyrup(Beverage beverage, int number) {
    super(beverage);
    this.number = number;
  }

  @Override
  public Integer getBasePrice() {
    return super.getBasePrice() + 800;
  }
}
