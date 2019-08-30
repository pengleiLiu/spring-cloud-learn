package org.lpl.server.application.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//标注在方法上
@Retention(RetentionPolicy.RUNTIME)//
@Documented
public @interface TimeOutCircuitBreaker {

  /**
   * 超时时间
   *
   * @return 设置超时时间
   */
  long timeout();
}
