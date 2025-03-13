package io.jeidiiy.sirenordersystem.product.domain.food.decorator.warming;

import io.jeidiiy.sirenordersystem.product.domain.food.Food;
import io.jeidiiy.sirenordersystem.product.domain.food.decorator.FoodDecorator;

public class Warming extends FoodDecorator {
  public Warming(Food food) {
    super(food);
  }
}
