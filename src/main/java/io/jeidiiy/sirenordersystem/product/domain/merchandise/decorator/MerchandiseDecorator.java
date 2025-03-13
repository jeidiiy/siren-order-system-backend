package io.jeidiiy.sirenordersystem.product.domain.merchandise.decorator;

import io.jeidiiy.sirenordersystem.product.domain.merchandise.Merchandise;

public abstract class MerchandiseDecorator extends Merchandise {
  protected final Merchandise decoratedMerchandise;

  public MerchandiseDecorator(Merchandise merchandise) {
    super(
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
