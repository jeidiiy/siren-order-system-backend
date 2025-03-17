package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.cup;

import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;

public class Cup extends BeverageDecorator {
  private final CupSize cupSize;

  public Cup(BeverageDto beverageDto, CupSize cupSize) {
    super(beverageDto);
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

  @Override
  public String getOptionKey() {
    return "cupSize";
  }

  @Override
  public Object getOptionValue() {
    return cupSize;
  }
}
