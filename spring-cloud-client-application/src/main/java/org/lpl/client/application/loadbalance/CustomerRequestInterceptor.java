package org.lpl.client.application.loadbalance;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2020-04-16 18:11
 **/
public class CustomerRequestInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
      ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

    HttpHeaders headers = httpRequest.getHeaders();
    headers.set("", "");

    return clientHttpRequestExecution.execute(httpRequest, bytes);
  }
}
