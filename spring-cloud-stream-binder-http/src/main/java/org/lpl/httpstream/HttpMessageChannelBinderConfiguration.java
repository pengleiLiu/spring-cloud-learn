package org.lpl.httpstream;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-23 18:23
 **/
@Configuration
public class HttpMessageChannelBinderConfiguration {

  @Bean
  public HttpMessageChannelBinder httpMessageChannelBinder(DiscoveryClient discoveryClient,
      MessageReceiverController controller) {
    return new HttpMessageChannelBinder(discoveryClient, controller);
  }
}
