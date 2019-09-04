package org.lpl.feignapi.service;

import org.lpl.feignapi.bean.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author lpl
 * @version 1.0
 * @date 2019/9/4 23:38
 **/
public interface OrderService {

  @GetMapping("/receiver")
  String sendMsg();

  @PostMapping("/receiverUser")
  User receiverUser(User user);
}
