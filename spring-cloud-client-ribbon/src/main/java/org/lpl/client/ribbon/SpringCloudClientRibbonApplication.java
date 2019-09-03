package org.lpl.client.ribbon;

import feign.Logger;
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
public class SpringCloudClientRibbonApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudClientRibbonApplication.class, args);
  }

  @Bean
  public Level feignLoggerLevel() {
    return Level.FULL;
  }

}
