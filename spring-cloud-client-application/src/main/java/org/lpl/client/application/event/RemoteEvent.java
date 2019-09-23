package org.lpl.client.application.event;

import org.springframework.context.ApplicationEvent;

/**
 * 远程事件
 *
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-20 09:48
 **/
public class RemoteEvent extends ApplicationEvent {

  /**
   * rpc、http、mq
   */
  private String type;
  /**
   * 应用名称
   */
  private final String appName;

  private final boolean isCluster;

  /**
   * Create a new ApplicationEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */
  public RemoteEvent(Object source, String appName, boolean isCluster) {
    super(source);
    this.appName = appName;
    this.isCluster = isCluster;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getAppName() {
    return appName;
  }
}
