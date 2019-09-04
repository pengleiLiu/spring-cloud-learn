package org.lpl.client.application.controller;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import java.util.Arrays;
import java.util.Collection;
import org.lpl.client.application.annotation.CustomLoadBalanced;
import org.lpl.client.application.loadbalance.LoadBalancedRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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
  @CustomLoadBalanced
  private RestTemplate restTemplate;

  @Autowired
  @LoadBalanced
  private RestTemplate rbRestTemplate;

  @RequestMapping("/client/{serviceName}/message")
  public String invokeMethod(@PathVariable String serviceName, @RequestParam String message) {
    return restTemplate
        .getForObject("/" + serviceName + "/message?message=" + message, String.class);
  }

  @RequestMapping("message")
  public String message(@RequestParam String message) {
    return message;
  }


  @RequestMapping("/rbClient/{serviceName}/message")
  public String rbInvokeMethod(@PathVariable String serviceName, @RequestParam String message) {
    return rbRestTemplate
        .getForObject("http://" + serviceName + "/rbMessage?message=" + message, String.class);
  }

  @RequestMapping("rbMessage")
  public String rbMessage(@RequestParam String message) {
    return message;
  }

  @Bean
  public ClientHttpRequestInterceptor interceptor() {
    return new LoadBalancedRequestInterceptor();
  }

  @LoadBalanced
  @Bean
  private RestTemplate rbRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  public IRule ribbonRUle() {
    return new RandomRule();
  }

  @Bean
  @CustomLoadBalanced
  public RestTemplate restTemplate() {
    //增加拦截器
//    RestTemplate restTemplate = new RestTemplate();
//    restTemplate.setInterceptors(Arrays.asList(interceptor));
    return new RestTemplate();
  }

  @Bean
  public Object addCustomerInterceptor(
      @CustomLoadBalanced Collection<RestTemplate> restTemplateList,
      ClientHttpRequestInterceptor interceptor) {

    restTemplateList.forEach(s -> {
      s.setInterceptors(Arrays.asList(interceptor));
    });
    return new Object();
  }

}
