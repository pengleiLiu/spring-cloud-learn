package org.lpl.client.application;

import feign.Logger.Level;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class SpringCloudClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudClientApplication.class, args);
  }

  @Bean
  public Level feignLoggerLevel() {
    return Level.FULL;
  }

}
