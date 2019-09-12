package org.lpl.stream.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * rabbit MQ消费者
 *
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-12 16:31
 **/
@EnableBinding(Sink.class)
public class SinkReceiver {

  private Logger logger = LoggerFactory.getLogger(SinkReceiver.class);

  @StreamListener(Sink.INPUT)
  public void receive(Object payload) {
    logger.info("Receiver:" + payload);
  }
}
