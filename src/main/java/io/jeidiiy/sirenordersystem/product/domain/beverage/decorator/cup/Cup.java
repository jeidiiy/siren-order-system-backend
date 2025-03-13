package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.cup;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;

public class Cup extends BeverageDecorator {
  private final CupSize cupSize;

  public Cup(Beverage beverage, CupSize cupSize) {
    super(beverage);
    this.cupSize = cupSize;
  }

  @Override
  public Integer getBasePrice() {
    return switch (cupSize) {
      case SHORT -> super.getBasePrice() - 800;
      case TALL -> super.getBasePrice();
      case GRANDE -> super.getBasePrice() + 600;
      case VENTI -> super.getBasePrice() + 1400;
    };
  }
}
