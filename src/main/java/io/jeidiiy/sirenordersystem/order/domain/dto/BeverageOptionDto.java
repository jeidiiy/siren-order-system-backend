package io.jeidiiy.sirenordersystem.order.domain.dto;

import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.bean.BeanType;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.cup.CupSize;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.temperature.Level;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.water.Amount;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record BeverageOptionDto(
    @NotNull(message = "원두 종류는 필수값입니다") BeanType beanType,
    @NotNull(message = "컵 크기는 필수값입니다") CupSize cupSize,
    @NotNull(message = "샷 횟수는 필수값입니다") Integer shots,
    Map<String, Integer> syrupOptions,
    Amount water,
    io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.ice.Amount ice,
    io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.whippedcream.Amount whippedCream,
    String etc,
    Level temperature) {}
