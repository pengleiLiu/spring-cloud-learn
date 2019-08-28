package org.lpl.user.controller;

//import com.netflix.discovery.DiscoveryClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-08-28 10:54
 **/
@RestController
public class UserController {

  private Logger logger = LoggerFactory.getLogger(UserController.class);

  private final Registration registration;

  private final DiscoveryClient discoveryClient;

  @Autowired
  public UserController(DiscoveryClient discoveryClient, Registration registration) {
    this.discoveryClient = discoveryClient;
    this.registration = registration;
  }

  @RequestMapping("hello")
  public String index() {

    logger.info("/hello,host:" + registration.getHost()
        + ",service_id:" + registration.getServiceId());

    return "hello spring cloud";
  }
}
