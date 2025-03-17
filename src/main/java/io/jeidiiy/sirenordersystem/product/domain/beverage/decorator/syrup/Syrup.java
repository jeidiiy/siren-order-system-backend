package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.syrup;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;
import lombok.Getter;

@Getter
public class Syrup extends BeverageDecorator {
  private final SyrupType syrupType;
  private final int pump;

  public Syrup(BeverageDto beverageDto, SyrupType syrupType, int pump) {
    super(beverageDto);
    this.syrupType = syrupType;
    this.pump = pump;
  }

  @Override
  public Integer getBasePrice() {
    return super.getBasePrice() + 800;
  }

  @Override
  public String getOptionKey() {
    return "syrup";
  }

  @Override
  public Object getOptionValue() {
    return pump;
  }
}
