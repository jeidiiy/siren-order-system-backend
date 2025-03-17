package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.whippedcream;

import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;

public class WhippedCream extends BeverageDecorator {
  private final Amount amount;

  public WhippedCream(BeverageDto beverageDto) {
    super(beverageDto);
    this.amount = Amount.ENOUGH;
  }

  public WhippedCream(BeverageDto beverageDto, Amount amount) {
    super(beverageDto);
    this.amount = amount;
  }

  @Override
  public String getOptionKey() {
    return "whippedCream";
  }

  @Override
  public Object getOptionValue() {
    return amount;
  }
}
