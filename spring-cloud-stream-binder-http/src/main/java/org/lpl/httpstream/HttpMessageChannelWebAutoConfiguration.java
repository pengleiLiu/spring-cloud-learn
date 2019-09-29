package org.lpl.httpstream;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-23 18:23
 **/
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
public class HttpMessageChannelWebAutoConfiguration {

  @Bean
  public MessageReceiverController messageReceiverController() {
    return new MessageReceiverController();
  }
}
