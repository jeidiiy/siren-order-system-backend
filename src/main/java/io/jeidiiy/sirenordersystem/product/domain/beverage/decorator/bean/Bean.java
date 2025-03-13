package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.bean;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;

public class Bean extends BeverageDecorator {
  private final BeanType beanType;

  public Bean(Beverage beverage) {
    super(beverage);
    this.beanType = BeanType.SIGNATURE;
  }

  public Bean(Beverage beverage, BeanType beanType) {
    super(beverage);
    this.beanType = beanType;
  }

  @Override
  public Integer getBasePrice() {
    return switch (beanType) {
      case DECAF, HALF_DECAF -> super.getBasePrice() + 300;
      default -> super.getBasePrice();
    };
  }
}
