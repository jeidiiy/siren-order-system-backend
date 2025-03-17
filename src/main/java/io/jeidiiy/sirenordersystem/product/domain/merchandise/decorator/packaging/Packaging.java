package io.jeidiiy.sirenordersystem.product.domain.merchandise.decorator.packaging;

import io.jeidiiy.sirenordersystem.product.domain.merchandise.Merchandise;
import io.jeidiiy.sirenordersystem.product.domain.merchandise.decorator.MerchandiseDecorator;
import io.jeidiiy.sirenordersystem.product.domain.merchandise.dto.MerchandiseDto;

public class Packaging extends MerchandiseDecorator {
  private final PackagingOption option;

  public Packaging(MerchandiseDto merchandiseDto, PackagingOption option) {
    super(merchandiseDto);
    this.option = option;
  }
}
