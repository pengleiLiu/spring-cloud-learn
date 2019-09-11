package org.lpl.client.application.controller;

import org.lpl.client.application.bean.User;
import org.lpl.client.application.service.feign.FeignConsumerService;
import org.lpl.client.application.service.feign.FeignHiConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-03 10:17
 **/
@RestController
public class FeignConsumerController {

  @Autowired
  private FeignConsumerService feignConsumerService;

//  @Autowired
//  private FeignHiConsumerService feignConsumerService;

  @RequestMapping("/feignConsumer")
  public String feignConsumer() {
    return feignConsumerService.sendMsg();
  }

  @RequestMapping("/feignConsumerUser")
  public User feignConsumerUser(@RequestBody User user) {
    return feignConsumerService.receiverUser(user);
  }
}
