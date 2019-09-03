package org.lpl.client.ribbon.service.feign;

import org.lpl.client.ribbon.bean.User;
import org.springframework.stereotype.Component;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-03 10:51
 **/
@Component
public class FeignConsumerServiceFallBack implements FeignConsumerService {

  @Override
  public String sendMsg() {
    return "error";
  }

  @Override
  public User receiverUser(User user) {

    return null;
  }
}
