package org.lpl.server.application.aop;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.lpl.server.application.annotation.SemaphoreCircuitBreaker;
import org.lpl.server.application.annotation.TimeOutCircuitBreaker;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-08-30 17:11
 **/
@Aspect
@Component
public class ServerControllerAspect {

  private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

  private volatile Semaphore semaphore = null;

  /**
   * 高级版熔断切面处理
   */
  @Around("execution(* org.lpl.server.application.controller.ServerController.advanceSay(..)) && args(message)")
  public Object advanceSayInTimeout(ProceedingJoinPoint point, String message) throws Throwable {
    return doInvoke(point, message, 100L);
  }


  @Around("execution(* org.lpl.server.application.controller.ServerController.annotationSay(..)) && args(message)")
  public Object advanceSayTimeOut(ProceedingJoinPoint point, String message) throws Throwable {

    Long timeout = 100L;

    if (point instanceof MethodInvocationProceedingJoinPoint) {

      MethodInvocationProceedingJoinPoint methodJoinPoint = (MethodInvocationProceedingJoinPoint) point;
      MethodSignature methodSignature = (MethodSignature) methodJoinPoint.getSignature();

      Method method = methodSignature.getMethod();

      TimeOutCircuitBreaker circuitBreaker = method.getAnnotation(TimeOutCircuitBreaker.class);
      timeout = circuitBreaker.timeout();
    }

    return doInvoke(point, message, timeout);
  }

  @Around("execution(* org.lpl.server.application.controller.ServerController."
      + "advancedSay3InSemaphore(..)) && args(message) && @annotation(semaphoreCircuitBreaker)")
  public Object semaphoreAdvanceSay(ProceedingJoinPoint point, String message,
      SemaphoreCircuitBreaker semaphoreCircuitBreaker) throws Throwable {

    int value = semaphoreCircuitBreaker.value();
    if (semaphore == null) {
      semaphore = new Semaphore(value);
    }

    Object returnValue = null;
    try {
      if (semaphore.tryAcquire()) {
        returnValue = point.proceed(new Object[]{message});
        Thread.sleep(1000);
      } else {
        returnValue = errorContent(message);
      }
    } finally {
      semaphore.release();
    }

    return returnValue;
  }


  public String errorContent(String message) {
    return "Fault";
  }

  private Object doInvoke(ProceedingJoinPoint point, String message, Long timeOut)
      throws Throwable {

    Future<Object> future = executorService.submit(() -> {
      try {
        return point.proceed(new Object[]{message});
      } catch (Throwable throwable) {
        return errorContent(message);
      }
    });

    Object returnValue = null;
    try {
      returnValue = future.get(100, TimeUnit.MILLISECONDS);
    } catch (TimeoutException e) {
      future.cancel(true);
      returnValue = errorContent(message);
    }
    return returnValue;
  }
}
