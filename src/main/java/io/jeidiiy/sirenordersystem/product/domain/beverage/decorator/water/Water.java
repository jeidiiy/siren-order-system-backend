package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.water;

import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;

public class Water extends BeverageDecorator {
  private final Amount amount;

  public Water(BeverageDto beverageDto) {
    super(beverageDto);
    amount = Amount.ENOUGH;
  }

  public Water(BeverageDto beverageDto, Amount amount) {
    super(beverageDto);
    this.amount = amount;
  }

  @Override
  public String getOptionKey() {
    return "water";
  }

  @Override
  public Object getOptionValue() {
    return amount;
  }
}
