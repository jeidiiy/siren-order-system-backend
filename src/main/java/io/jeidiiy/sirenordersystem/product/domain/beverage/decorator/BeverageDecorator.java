package io.jeidiiy.sirenordersystem.product.domain.beverage.decorator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;
import lombok.Getter;

@Getter
public abstract class BeverageDecorator extends BeverageDto {
  protected final BeverageDto decoratedBeverage;

  public BeverageDecorator(BeverageDto beverageDto) {
    super(
        beverageDto.getId(),
        beverageDto.getKrName(),
        beverageDto.getEnName(),
        beverageDto.getDescription(),
        beverageDto.getBasePrice(),
        beverageDto.getImageUrl());
    this.decoratedBeverage = beverageDto;
  }

  @Override
  public Integer getBasePrice() {
    return decoratedBeverage.getBasePrice();
  }

  public abstract String getOptionKey();

  public abstract Object getOptionValue();
}
