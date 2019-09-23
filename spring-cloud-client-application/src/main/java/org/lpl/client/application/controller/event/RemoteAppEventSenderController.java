package org.lpl.client.application.controller.event;

import org.lpl.client.application.event.RemoteEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义远程事件发送者
 *
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-23 15:14
 **/
@RestController
public class RemoteAppEventSenderController implements
    ApplicationEventPublisherAware {


  private ApplicationEventPublisher publisher;

  @PostMapping("send/remote/event/{appName}")
  public String sendAppCluster(@PathVariable String appName, @RequestBody Object data) {

    RemoteEvent remoteEvent = new RemoteEvent(data, appName, true);
    publisher.publishEvent(remoteEvent);

    return "OK";
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.publisher = applicationEventPublisher;
  }
}
