package org.lpl.client.application.service.feign;

import org.lpl.client.application.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-03 10:13
 **/
@FeignClient(name = "server-order", fallback = FeignConsumerServiceFallBack.class)
public interface FeignConsumerService {

  @GetMapping("/receiver")
  String sendMsg();

  @PostMapping("/receiverUser")
  User receiverUser(User user);

}
