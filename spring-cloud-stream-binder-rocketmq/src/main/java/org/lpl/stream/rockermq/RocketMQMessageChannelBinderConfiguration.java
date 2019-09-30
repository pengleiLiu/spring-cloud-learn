package org.lpl.stream.rockermq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-30 15:24
 **/
@Configuration
public class RocketMQMessageChannelBinderConfiguration {

  @Bean
  public RocketMQMessageChannelBinder rocketMQMessageChannelBinder() {
    return new RocketMQMessageChannelBinder();
  }
}
