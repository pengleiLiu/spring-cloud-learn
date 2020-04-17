package org.lpl.client.application.controller;

import org.lpl.client.application.auth.ServerApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2020-04-16 18:13
 **/
@RestController
@RequestMapping("server")
public class CustomerController {

  @Autowired
  ServerApplicationService service;

  @GetMapping("header")
  public String testResult() {

    return service.getMessage();
  }

//  @Bean
//  public RestTemplate restTemplate(RestTemplateBuilder builder) {
//    RestTemplate restTemplate = builder.interceptors(new CustomerRequestInterceptor()).build();
//    return restTemplate;
//  }

}
