package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.ice;

import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;

public class Ice extends BeverageDecorator {
  private final Amount amount;

  public Ice(BeverageDto beverageDto) {
    super(beverageDto);
    this.amount = Amount.ENOUGH;
  }

  public Ice(BeverageDto beverageDto, Amount amount) {
    super(beverageDto);
    this.amount = amount;
  }

  @Override
  public String getOptionKey() {
    return "ice";
  }

  @Override
  public Object getOptionValue() {
    return amount;
  }
}
