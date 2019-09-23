package org.lpl.server.application;

import java.io.UnsupportedEncodingException;
import javax.annotation.PostConstruct;
import org.lpl.server.application.stream.CustomerMessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
//@EnableBinding(CustomerMessageReceiver.class)//激活并引入 SimpleMessageReceiver
public class SpringCloudServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudServerApplication.class, args);
  }

  //@Autowired
  private CustomerMessageReceiver customerMessageReceiver;

  //@PostConstruct
  public void init() {  // 接口编程
    // 获取 SubscribableChannel
    SubscribableChannel subscribableChannel = customerMessageReceiver.lplStream();
    subscribableChannel.subscribe(message -> {
      MessageHeaders headers = message.getHeaders();
      String encoding = (String) headers.get("charset-encoding");
      String text = (String) headers.get("content-type");
      byte[] content = (byte[]) message.getPayload();
      try {
        System.out.println("接受到消息：" + new String(content, encoding));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    });
  }

  @StreamListener("lpl-stream")
  public void onMessage(byte[] data) {
    System.out.println("onMessage(byte[]): " + new String(data));
  }

}
