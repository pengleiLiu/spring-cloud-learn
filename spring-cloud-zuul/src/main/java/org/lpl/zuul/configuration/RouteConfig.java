package org.lpl.zuul.configuration;

import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-06 15:43
 **/
@Configuration
public class RouteConfig {

  @Bean
  public PatternServiceRouteMapper serviceRouteMapper() {

    return new PatternServiceRouteMapper("(?<name>^.+):(?<version>v.+$)", "${version}/${name}");
  }
}
