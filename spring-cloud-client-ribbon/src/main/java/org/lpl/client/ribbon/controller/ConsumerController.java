package org.lpl.client.ribbon.controller;

import org.lpl.client.ribbon.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-08-30 11:26
 **/
@RestController
public class ConsumerController {

  @Autowired
  private ConsumerService consumerService;

  @RequestMapping("/sendMsg")
  public String sendMsg(){
    return consumerService.sendMessage();
  }
}
