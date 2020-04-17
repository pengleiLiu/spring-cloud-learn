package org.lpl.client.application.auth;

import feign.Client;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2020-04-17 14:15
 **/
@Configuration
public class CustomerFeignConfig {

  @Bean
  @ConditionalOnMissingBean
  public Client feignClient(CachingSpringLoadBalancerFactory cachingFactory,
      SpringClientFactory clientFactory) {
    return new CustomerLoadBalancerFeignClient(new Client.Default(null, null), cachingFactory,
        clientFactory);
  }
}
