package io.jeidiiy.sirenordersystem.product.domain.merchandise.decorator;

import io.jeidiiy.sirenordersystem.product.domain.merchandise.dto.MerchandiseDto;

public abstract class MerchandiseDecorator extends MerchandiseDto {
  protected final MerchandiseDto decoratedMerchandise;

  public MerchandiseDecorator(MerchandiseDto merchandise) {
    super(
        merchandise.getId(),
        merchandise.getKrName(),
        merchandise.getEnName(),
        merchandise.getDescription(),
        merchandise.getBasePrice(),
        merchandise.getImageUrl());
    this.decoratedMerchandise = merchandise;
  }

  @Override
  public Integer getBasePrice() {
    return decoratedMerchandise.getBasePrice();
  }
}
