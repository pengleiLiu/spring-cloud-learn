package org.lpl.server.application.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-16 13:57
 **/
public interface CustomerMessageReceiver {

//  @Input("lpl-stream")
//  SubscribableChannel lplStreamChannel();
//
//  @Input("test-http")
//  SubscribableChannel testHttpChannel();

  @Input("test-rocketmq")
  SubscribableChannel rocketMQChannel();
}
