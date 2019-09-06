package org.lpl.zuul.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-06 16:41
 **/
@RestController
public class LocalController {

  @RequestMapping("/local/receiver")
  public String local() {

    return "local hello";
  }
}
