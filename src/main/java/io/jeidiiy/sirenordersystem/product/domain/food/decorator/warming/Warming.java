package io.jeidiiy.sirenordersystem.product.domain.food.decorator.warming;

import io.jeidiiy.sirenordersystem.product.domain.food.decorator.FoodDecorator;
import io.jeidiiy.sirenordersystem.product.domain.food.dto.FoodDto;

public class Warming extends FoodDecorator {
  public Warming(FoodDto food) {
    super(food);
  }
}
