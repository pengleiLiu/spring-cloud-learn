package org.lpl.zuul;

import org.lpl.zuul.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy  //开启Zuul
@SpringCloudApplication
public class SpringCloudZuulApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudZuulApplication.class, args);
  }

  @Bean
  public AccessFilter accessFilter() {
    return new AccessFilter();
  }
}
