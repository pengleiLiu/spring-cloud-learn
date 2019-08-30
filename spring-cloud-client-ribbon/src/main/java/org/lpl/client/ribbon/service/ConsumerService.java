package org.lpl.client.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-08-30 11:27
 **/
@Service
public class ConsumerService {

  @Autowired
  @LoadBalanced
  private RestTemplate restTemplate;

  @HystrixCommand(fallbackMethod = "sendMsgFallBack", commandKey = "sendMessage", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1200")}) //设置超时时间
  public String sendMessage() {
    return restTemplate.getForEntity("http://SERVER-ORDER/receiver", String.class)
        .getBody();
  }

  public String sendMsgFallBack() {
    return "error";
  }


}
