package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.shot;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;

public class Shot extends BeverageDecorator {
  private static final Integer DEFAULT_SHOTS = 2;
  private static final Integer PRICE_PER_SHOT = 800;
  private final Integer number;

  public Shot(BeverageDto beverageDto, Integer number) {
    super(beverageDto);
    this.number = number;
  }

  @Override
  public Integer getBasePrice() {
    if (number > DEFAULT_SHOTS) {
      return super.getBasePrice() + (PRICE_PER_SHOT * (number - DEFAULT_SHOTS));
    }
    return super.getBasePrice();
  }

  @Override
  public String getOptionKey() {
    return "shots";
  }

  @Override
  public Integer getOptionValue() {
    return number;
  }
}
