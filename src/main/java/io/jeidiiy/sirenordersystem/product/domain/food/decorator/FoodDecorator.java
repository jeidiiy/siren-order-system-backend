package io.jeidiiy.sirenordersystem.product.domain.food.decorator;

import io.jeidiiy.sirenordersystem.product.domain.food.dto.FoodDto;

public abstract class FoodDecorator extends FoodDto {
  protected final FoodDto decoratedFood;

  public FoodDecorator(FoodDto foodDto) {
    super(
        foodDto.getId(),
        foodDto.getKrName(),
        foodDto.getEnName(),
        foodDto.getDescription(),
        foodDto.getBasePrice(),
        foodDto.getImageUrl());
    this.decoratedFood = foodDto;
  }

  @Override
  public Integer getBasePrice() {
    return decoratedFood.getBasePrice();
  }
}
