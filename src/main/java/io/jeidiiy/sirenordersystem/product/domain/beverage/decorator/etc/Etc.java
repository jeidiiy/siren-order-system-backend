package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.etc;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;

public class Etc extends BeverageDecorator {
  private final String message = "여유 공간 남겨주세요";

  public Etc(Beverage beverage) {
    super(beverage);
  }
}
