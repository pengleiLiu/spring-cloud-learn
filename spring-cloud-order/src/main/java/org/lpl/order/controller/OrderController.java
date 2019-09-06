package org.lpl.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-08-28 17:41
 **/
@RestController
public class OrderController {


  @Autowired
  private RestTemplate restTemplate;

  @RequestMapping("user")
  public String getOrder() {

    String body = restTemplate.getForEntity("http://SERVER-USER/hello", String.class).getBody();

    return body + "lpl";
  }

  @RequestMapping("v1/receiver")
  public String receiverV1() {
    return "v1/receiver";
  }

  @RequestMapping("v2/receiver")
  public String receiverV2() {
    return "v2/receiver";
  }
}
