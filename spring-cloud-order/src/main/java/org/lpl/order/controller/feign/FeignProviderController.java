package org.lpl.order.controller.feign;

import org.lpl.feignapi.bean.User;
import org.lpl.feignapi.service.OrderService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lpl
 * @version 1.0
 * @date 2019/9/5
 **/
@RestController
public class FeignProviderController implements OrderService {

  @Override
  public String sendMsg() {
    System.out.println(1);
    return "hello";
  }

  @Override
  public User receiverUser(User user) {
    return user;
  }
}
