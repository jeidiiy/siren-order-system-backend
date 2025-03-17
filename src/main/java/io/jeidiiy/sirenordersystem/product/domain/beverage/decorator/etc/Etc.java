package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.etc;

import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;

public class Etc extends BeverageDecorator {
  private final String message;

  public Etc(BeverageDto beverageDto, String message) {
    super(beverageDto);
    this.message = message;
  }

  @Override
  public String getOptionKey() {
    return "etc";
  }

  @Override
  public Object getOptionValue() {
    return message;
  }
}
