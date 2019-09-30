package org.lpl.stream.controller;

import java.util.HashMap;
import java.util.Map;
import org.lpl.stream.message.CustomerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-16 11:39
 **/
@RestController
public class MessageController {

  private CustomerMessage customerMessage;

  @Autowired
  public void setCustomerMessage(CustomerMessage customerMessage) {
    this.customerMessage = customerMessage;
  }

  @RequestMapping("stream/send")
  public void sendMsg(@RequestParam String message) {

    MessageChannel messageChannel = customerMessage.rocketMQ();
    Map<String, Object> headers = new HashMap<>();
    headers.put("charset-encoding", "UTF-8");
    headers.put("content-type", MediaType.TEXT_PLAIN_VALUE);

    messageChannel.send(new GenericMessage<>(message, headers));
  }

  @GetMapping("/stream/send/http")
  public boolean streamSendToHttp(@RequestParam String message) {
    // 获取 MessageChannel
    MessageChannel messageChannel = customerMessage.rocketMQ();
    return messageChannel.send(new GenericMessage<>(message));
  }

  @GetMapping("/stream/send/rocketmq")
  public boolean streamSendToRockectMQ(@RequestParam String message) {

    MessageChannel messageChannel = customerMessage.rocketMQ();
    return messageChannel.send(new GenericMessage<>(message));
  }
}
