package io.jeidiiy.sirenordersystem.store.exception;

import io.jeidiiy.sirenordersystem.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class StoreNotFoundException extends ClientErrorException {

  public StoreNotFoundException(Integer storeId) {
    super(HttpStatus.NOT_FOUND, "매장을 찾지 못했습니다. storeId: " + storeId);
  }

  public StoreNotFoundException(Integer storeId, String username) {
    super(HttpStatus.NOT_FOUND, "매장을 찾지 못했습니다. storeId: " + storeId + ", username: " + username);
  }
}
