package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;

public abstract class BeverageDecorator extends Beverage {
  protected final Beverage decoratedBeverage;

  public BeverageDecorator(Beverage beverage) {
    super(
        beverage.getKrName(),
        beverage.getEnName(),
        beverage.getDescription(),
        beverage.getBasePrice(),
        beverage.getImageUrl());
    this.decoratedBeverage = beverage;
  }

  @Override
  public Integer getBasePrice() {
    return decoratedBeverage.getBasePrice();
  }
}
