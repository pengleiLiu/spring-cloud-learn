package org.lpl.client.ribbon.controller;

import org.lpl.client.ribbon.service.ConsumerService;
import org.lpl.client.ribbon.service.ConsumerServiceCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-08-30 11:26
 **/
@RestController
public class ConsumerController {

  @Autowired
  private ConsumerService consumerService;

  @Autowired
  @LoadBalanced
  private RestTemplate restTemplate;

  @RequestMapping("/sendMsg")
  public String sendMsg() {
    return consumerService.sendMessage();
  }


  @RequestMapping("/sendMsgCommand")
  public String sendMsgCommand() {

    ConsumerServiceCommand consumerServiceCommand = new ConsumerServiceCommand(restTemplate);
    String execute = consumerServiceCommand.execute();
    return execute;
  }
}
