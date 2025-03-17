package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.bean;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;

public class Bean extends BeverageDecorator {
  private final BeanType beanType;

  public Bean(BeverageDto beverageDto) {
    super(beverageDto);
    this.beanType = BeanType.SIGNATURE;
  }

  public Bean(BeverageDto beverageDto, BeanType beanType) {
    super(beverageDto);
    this.beanType = beanType;
  }

  @Override
  public Integer getBasePrice() {
    return switch (beanType) {
      case DECAF, HALF_DECAF -> super.getBasePrice() + 300;
      default -> super.getBasePrice();
    };
  }

  @Override
  public String getOptionKey() {
    return "beanType";
  }

  @Override
  public Object getOptionValue() {
    return beanType;
  }
}
