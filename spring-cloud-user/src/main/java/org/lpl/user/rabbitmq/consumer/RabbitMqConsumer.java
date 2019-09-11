package org.lpl.user.rabbitmq.consumer;

import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-11 14:23
 **/
@Component
public class RabbitMqConsumer {

  private static final Logger logger = LoggerFactory.getLogger(RabbitMqConsumer.class);

  @RabbitListener(queues = "spring-cloud-bus-queue")
  public void consumer(Message msg) {

    String message = new String(msg.getBody(), StandardCharsets.UTF_8);

    logger.info("收到来自[内容服务]消息:{}", message);
  }
}
