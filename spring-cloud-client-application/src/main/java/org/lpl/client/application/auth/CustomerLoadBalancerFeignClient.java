package org.lpl.client.application.auth;


import java.io.IOException;

import feign.Client;
import feign.Request;
import feign.Response;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2020-04-17 14:09
 **/
public class CustomerLoadBalancerFeignClient extends LoadBalancerFeignClient {

  public CustomerLoadBalancerFeignClient(Client delegate,
      CachingSpringLoadBalancerFactory lbClientFactory, SpringClientFactory clientFactory) {
    super(delegate, lbClientFactory, clientFactory);
  }

  @Override
  public Response execute(Request request, Request.Options options) throws IOException {

    System.out.println(11);

    return super.execute(request, options);
  }
}
