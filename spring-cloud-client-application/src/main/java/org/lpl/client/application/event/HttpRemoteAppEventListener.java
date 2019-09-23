package org.lpl.client.application.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * http 远程事件监听
 *
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-23 10:38
 **/
@Component
public class HttpRemoteAppEventListener implements SmartApplicationListener {

  private RestTemplate restTemplate = new RestTemplate();

  private DiscoveryClient discoveryClient;

  private String currentName;

  private void onApplicationEvent(RemoteEvent event) {

    Object source = event.getSource();
    String appName = event.getAppName();

    List<ServiceInstance> serviceInstances = discoveryClient.getInstances(appName);

    for (ServiceInstance s : serviceInstances) {
      String rootUrl = s.isSecure() ? "https://" + s.getHost() + ":" + s.getPort()
          : "http://" + s.getHost() + ":" + s.getPort();

      String url = rootUrl + "/receive/remote/event";

      Map<String, Object> requestData = new HashMap<>();
      requestData.put("sender", currentName);
      requestData.put("value", source);
      requestData.put("type", RemoteEvent.class.getName());

      restTemplate.postForObject(url, requestData, String.class);
    }
  }

  private void onContextRefreshedEvent(ContextRefreshedEvent event) {

    ApplicationContext applicationContext = event.getApplicationContext();
    this.discoveryClient = applicationContext.getBean(DiscoveryClient.class);
    this.currentName = applicationContext.getEnvironment().getProperty("spring.application.name");
  }

  @Override
  public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
    return RemoteEvent.class.isAssignableFrom(eventType)
        || ContextRefreshedEvent.class.isAssignableFrom(eventType);
  }

  @Override
  public void onApplicationEvent(ApplicationEvent event) {

    if (event instanceof RemoteEvent) {
      onApplicationEvent((RemoteEvent) event);
    } else if (event instanceof ContextRefreshedEvent) {
      onContextRefreshedEvent((ContextRefreshedEvent) event);
    }
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
