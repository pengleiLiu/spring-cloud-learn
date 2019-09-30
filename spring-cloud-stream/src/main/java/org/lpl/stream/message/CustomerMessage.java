package org.lpl.stream.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-16 11:41
 **/
public interface CustomerMessage {

//  @Output("lpl-stream")// Channel name
//  MessageChannel lplStream();
//
//  @Output("test-http")// Channel name
//  MessageChannel testHttp(); //destination

  @Output("test-rocketmq")
  MessageChannel rocketMQ();
}
