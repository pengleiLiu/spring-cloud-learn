package org.lpl.server.application.controller.event;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * http 远程事件接受
 *
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-23 15:22
 **/
@RestController
public class RemoteAppEventReceiverController implements ApplicationEventPublisherAware {

  private Logger logger = LoggerFactory.getLogger(RemoteAppEventReceiverController.class);

  private ApplicationEventPublisher publisher;

  @PostMapping("receive/remote/event")
  public String receiver(@RequestBody Map<String, Object> requestMap) {

    String sender = (String) requestMap.get("sender");
    Object value = requestMap.get("value");
    String type = (String) requestMap.get("type");

    logger.info("接收到事件:{}", JSONObject.toJSONString(requestMap));

    publisher.publishEvent(new SenderRemoteAppEvent(value, sender));

    return "receive";
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.publisher = applicationEventPublisher;
  }

  private static class SenderRemoteAppEvent extends ApplicationEvent {

    private final String sender;

    public SenderRemoteAppEvent(Object source, String sender) {
      super(source);
      this.sender = sender;
    }

    public String getSender() {
      return sender;
    }
  }

  @EventListener
  @Async
  public void onEvent(SenderRemoteAppEvent remoteAppEvent) {
    logger.info("接收到事件名称:{},事件源:{}", remoteAppEvent.getClass().getSimpleName(),
        remoteAppEvent.getSender());
  }

}
