package org.lpl.client.ribbon.controller;

import java.util.Arrays;
import org.lpl.client.ribbon.loadbalance.LoadBalancedRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-08-29 17:33
 **/
@RestController
public class ClientController {

  @Autowired
  private RestTemplate restTemplate;

  @RequestMapping("/client/{serviceName}/message")
  public String invokeMethod(@PathVariable String serviceName, @RequestParam String message) {
    return restTemplate
        .getForObject("/" + serviceName + "/message?message=" + message, String.class);
  }

  @RequestMapping("message")
  public String message(@RequestParam String message) {
    return message;
  }

  @Bean
  public ClientHttpRequestInterceptor interceptor() {
    return new LoadBalancedRequestInterceptor();
  }

  @Bean
  public RestTemplate restTemplate(ClientHttpRequestInterceptor interceptor) {

    //增加拦截器
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setInterceptors(Arrays.asList(interceptor));

    return restTemplate;
  }

}
