package org.lpl.client.application.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2020-04-16 18:07
 **/
@FeignClient(name = "spring-cloud-server", configuration = CustomerFeignClientInterceptor.class)
public interface ServerApplicationService {

  @GetMapping("/getMessage")
  String getMessage();
}
