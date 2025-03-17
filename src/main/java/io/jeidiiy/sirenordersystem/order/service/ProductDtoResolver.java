package io.jeidiiy.sirenordersystem.order.service;

import io.jeidiiy.sirenordersystem.order.domain.dto.BeverageOptionDto;
import io.jeidiiy.sirenordersystem.order.domain.dto.FoodOptionDto;
import io.jeidiiy.sirenordersystem.order.domain.dto.MerchandiseOptionDto;
import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.bean.Bean;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.cup.Cup;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.etc.Etc;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.ice.Ice;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.shot.Shot;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.syrup.Syrup;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.syrup.SyrupType;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.temperature.Temperature;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.water.Water;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.whippedcream.WhippedCream;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;
import io.jeidiiy.sirenordersystem.product.domain.dto.ProductDto;
import io.jeidiiy.sirenordersystem.product.domain.food.Food;
import io.jeidiiy.sirenordersystem.product.domain.food.decorator.warming.Warming;
import io.jeidiiy.sirenordersystem.product.domain.food.dto.FoodDto;
import io.jeidiiy.sirenordersystem.product.domain.merchandise.Merchandise;
import io.jeidiiy.sirenordersystem.product.domain.merchandise.decorator.packaging.Packaging;
import io.jeidiiy.sirenordersystem.product.domain.merchandise.dto.MerchandiseDto;
import io.jeidiiy.sirenordersystem.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Component
public class ProductDtoResolver {
  private final ProductService productService;

  public ProductDto resolveBeverageItemDto(Integer productId, BeverageOptionDto beverageOptionDto) {
    Beverage beverage = (Beverage) productService.findById(productId);
    BeverageDto beverageDto = (BeverageDto) ProductDto.from(beverage);

    beverageDto = new Bean(beverageDto, beverageOptionDto.beanType());
    beverageDto = new Cup(beverageDto, beverageOptionDto.cupSize());
    beverageDto = new Shot(beverageDto, beverageOptionDto.shots());
    if (!beverageOptionDto.syrupOptions().isEmpty()) {
      Map<String, Integer> syrupOptions = beverageOptionDto.syrupOptions();
      for (Map.Entry<String, Integer> syrupOption : syrupOptions.entrySet()) {
        beverageDto =
            new Syrup(
                beverageDto,
                SyrupType.valueOf(syrupOption.getKey().toUpperCase()),
                syrupOption.getValue());
      }
    }
    if (!ObjectUtils.isEmpty(beverageOptionDto.water())) {
      beverageDto = new Water(beverageDto, beverageOptionDto.water());
    }
    if (!ObjectUtils.isEmpty(beverageOptionDto.ice())) {
      beverageDto = new Ice(beverageDto, beverageOptionDto.ice());
    }
    if (!ObjectUtils.isEmpty(beverageOptionDto.whippedCream())) {
      beverageDto = new WhippedCream(beverageDto, beverageOptionDto.whippedCream());
    }
    if (!ObjectUtils.isEmpty(beverageOptionDto.temperature())) {
      beverageDto = new Temperature(beverageDto, beverageOptionDto.temperature());
    }
    if (!ObjectUtils.isEmpty(beverageOptionDto.etc())) {
      beverageDto = new Etc(beverageDto, beverageOptionDto.etc());
    }

    return beverageDto;
  }

  public ProductDto resolveFoodItemDto(Integer productId, FoodOptionDto foodOptionDto) {
    Food food = (Food) productService.findById(productId);
    FoodDto foodDto = (FoodDto) FoodDto.from(food);
    if (!ObjectUtils.isEmpty(foodOptionDto.warming())) {
      foodDto = new Warming(foodDto);
    }
    return foodDto;
  }

  public ProductDto resolveMerchandiseItemDto(
      Integer productId, MerchandiseOptionDto merchandiseOptionDto) {
    Merchandise merchandise = (Merchandise) productService.findById(productId);
    MerchandiseDto merchandiseDto = (MerchandiseDto) MerchandiseDto.from(merchandise);
    if (!ObjectUtils.isEmpty(merchandiseOptionDto.option())) {
      merchandiseDto = new Packaging(merchandiseDto, merchandiseOptionDto.option());
    }
    return merchandiseDto;
  }
}
