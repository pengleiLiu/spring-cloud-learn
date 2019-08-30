package org.lpl.server.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableCircuitBreaker
@SpringBootApplication
public class SpringCloudServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudServerApplication.class, args);
  }

}
