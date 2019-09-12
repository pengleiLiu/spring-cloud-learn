package org.lpl.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-08-27 16:32
 **/
@RefreshScope
@RestController
@RequestMapping("config")
public class ConfigController {

  @Value("${info.name}")
  private String name;

  @Value("${info.age}")
  private String age;

  @Value("${my.name}")
  private String myName;


  @RequestMapping("name")
  public String health() {
    return "[config.name]:" + name + "age:" + age;
  }

  @RequestMapping("getName")
  public String getMyName() {
    return myName;
  }

}
