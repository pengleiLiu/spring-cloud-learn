package org.lpl.client.ribbon.service.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-02 18:25
 **/
@Service
public class ConsumerServiceCommand extends HystrixCommand<String> {

  private RestTemplate restTemplate;

  /**
   * 定义分组、线程组
   */
  public ConsumerServiceCommand(RestTemplate restTemplate) {
    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("consumerServiceGroupKey"))
        .andCommandKey(HystrixCommandKey.Factory.asKey("consumerServiceCommandKey"))
        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("consumerThreadPoolKey")));
    this.restTemplate = restTemplate;
  }

  @Override
  protected String run() throws Exception {
    return restTemplate.getForEntity("http://SERVER-ORDER/receiver", String.class)
        .getBody();
  }

  @Override
  protected String getFallback() {
    return "error";
  }
}
