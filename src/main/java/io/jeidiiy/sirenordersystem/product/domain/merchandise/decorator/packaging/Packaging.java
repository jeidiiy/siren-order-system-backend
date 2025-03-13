package io.jeidiiy.sirenordersystem.product.domain.merchandise.decorator.packaging;

import io.jeidiiy.sirenordersystem.product.domain.merchandise.Merchandise;
import io.jeidiiy.sirenordersystem.product.domain.merchandise.decorator.MerchandiseDecorator;

public class Packaging extends MerchandiseDecorator {
  private final Packaging packaging;

  public Packaging(Merchandise merchandise, Packaging packaging) {
    super(merchandise);
    this.packaging = packaging;
  }
}
