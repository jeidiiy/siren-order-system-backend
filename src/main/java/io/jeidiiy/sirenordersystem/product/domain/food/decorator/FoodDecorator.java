package io.jeidiiy.sirenordersystem.product.domain.food.decorator;

import io.jeidiiy.sirenordersystem.product.domain.food.Food;

public abstract class FoodDecorator extends Food {
  protected final Food decoratedFood;

  public FoodDecorator(Food food) {
    super(
        food.getKrName(),
        food.getEnName(),
        food.getDescription(),
        food.getBasePrice(),
        food.getImageUrl());
    this.decoratedFood = food;
  }

  @Override
  public Integer getBasePrice() {
    return decoratedFood.getBasePrice();
  }
}
