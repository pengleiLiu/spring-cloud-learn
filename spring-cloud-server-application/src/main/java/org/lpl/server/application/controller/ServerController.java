package org.lpl.server.application.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.lpl.server.application.annotation.SemaphoreCicuitBreaker;
import org.lpl.server.application.annotation.TimeOutCircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-08-30 16:07
 **/
@RestController
public class ServerController {

  private static final Random random = new Random();

  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  /**
   * 简易版熔断,利用Future的get
   */
  @GetMapping("/say1")
  public String say1(@RequestParam String message) {

    Future<String> future = executorService.submit(() -> doRequest(message));

    String returnValue = null;
    try {
      returnValue = future.get(100, TimeUnit.MILLISECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      returnValue = errorContent(message);
    }
    return returnValue;
  }

  /**
   * 通过切面实现熔断器
   */
  @GetMapping("advanced/say1")
  public String advanceSay(@RequestParam String message) throws Exception {
    return doRequest(message);
  }

  /**
   * 切面+注解实现
   */
  @GetMapping("advanced/say2")
  @TimeOutCircuitBreaker(timeout = 100)
  public String annotationSay(@RequestParam String message) throws Exception {
    return doRequest(message);
  }

  @GetMapping("advanced/say3")
  @SemaphoreCicuitBreaker(1)
  public String advancedSay3InSemaphore(@RequestParam String message) throws Exception {
    return doRequest(message);
  }

  private String doRequest(String request) throws InterruptedException {
    int processTime = random.nextInt(200);
    System.out.println("process costs" + processTime + "ms .");

    Thread.sleep(processTime);
    String responseStr = "response:" + request;
    System.out.println(responseStr);

    return responseStr;
  }

  /**
   * SpringCloud - 熔断器
   */
  @HystrixCommand(
      fallbackMethod = "errorContent",
      commandProperties = {
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
              value = "100")
      }
  )
  @GetMapping("/say")
  public String say(@RequestParam String message) throws InterruptedException {
    // 如果随机时间 大于 100 ，那么触发容错
    int value = random.nextInt(200);

    System.out.println("say() costs " + value + " ms.");

    // > 100
    Thread.sleep(value);

    System.out.println("ServerController 接收到消息 - say : " + message);
    return "Hello, " + message;
  }

  public String errorContent(String message) {
    return "Fault";
  }
}
