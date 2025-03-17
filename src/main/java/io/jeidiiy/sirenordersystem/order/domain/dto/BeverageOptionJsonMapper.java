package io.jeidiiy.sirenordersystem.order.domain.dto;

import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.BeverageDecorator;
import io.jeidiiy.sirenordersystem.product.domain.beverage.decorator.syrup.Syrup;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class BeverageOptionJsonMapper {

  public static Map<String, Object> convertDecoratedBeverageToMap(BeverageDto beverageDto) {
    Map<String, Object> options = new HashMap<>();

    // 데코레이터 순회하면서 옵션 저장
    while (beverageDto instanceof BeverageDecorator decorator) {
      String key = decorator.getOptionKey();
      Object value = decorator.getOptionValue();

      if ("syrup".equals(key)) {
        // 시럽 옵션을 Map<String, Integer> 형태로 저장
        if (!options.containsKey(key)) {
          options.put(key, new HashMap<String, Integer>());
        }
        Map<String, Integer> syrupMap = (Map<String, Integer>) options.get(key);
        Syrup syrupDecorator = (Syrup) decorator;
        syrupMap.put(syrupDecorator.getSyrupType().name().toLowerCase(), syrupDecorator.getPump());
      } else {
        options.put(key, value);
      }

      beverageDto = decorator.getDecoratedBeverage();
    }

    return options;
  }
}
