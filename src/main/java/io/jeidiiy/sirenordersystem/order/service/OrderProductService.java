package io.jeidiiy.sirenordersystem.order.service;

import io.jeidiiy.sirenordersystem.order.domain.OrderProduct;
import io.jeidiiy.sirenordersystem.order.repository.OrderProductJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderProductService {
  private final OrderProductJpaRepository orderProductJpaRepository;

  public void save(OrderProduct orderProduct) {
    orderProductJpaRepository.save(orderProduct);
  }

  public List<OrderProduct> findAllByOrderId(Integer orderId) {
    return orderProductJpaRepository.findAllByOrderId(orderId);
  }
}
