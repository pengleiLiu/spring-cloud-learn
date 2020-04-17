package org.lpl.client.application.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2020-04-17 10:17
 **/
@Configuration
public class CustomerFeignClientInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate template) {

    String url = template.url();
  }
}
