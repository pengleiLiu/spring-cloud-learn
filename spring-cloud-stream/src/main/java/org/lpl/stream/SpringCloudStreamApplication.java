package org.lpl.stream;

import org.lpl.stream.message.CustomerMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(CustomerMessage.class)
public class SpringCloudStreamApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudStreamApplication.class, args);
  }

}
