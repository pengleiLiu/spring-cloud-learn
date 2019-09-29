package org.lpl.httpstream;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.client.RestTemplate;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-24 10:21
 **/
public class HttpMessageChannelBinder implements
    Binder<MessageChannel, ConsumerProperties, ProducerProperties> {

  private String TARGET_APPNAME = "spring-cloud-server";

  private final DiscoveryClient discoveryClient;

  private final MessageReceiverController messageReceiverController;

  public HttpMessageChannelBinder(DiscoveryClient discoveryClient,
      MessageReceiverController messageReceiverController) {
    this.discoveryClient = discoveryClient;
    this.messageReceiverController = messageReceiverController;
  }

  public Binding<MessageChannel> bindConsumer(String name, String group,
      MessageChannel inputChannel, ConsumerProperties consumerProperties) {
    //TODO 如果不进行判断会被最后一个MessageChannel覆盖
    if (name.equals("test-http")) {
      messageReceiverController.setInputChannel(inputChannel);
    }
    return null;
  }

  public Binding<MessageChannel> bindProducer(String name, MessageChannel outputChannel,
      ProducerProperties producerProperties) {

    RestTemplate restTemplate = new RestTemplate();

    SubscribableChannel subscribableChannel = (SubscribableChannel) outputChannel;

    subscribableChannel.subscribe(message -> {

      byte[] messageBody = (byte[]) message.getPayload();

      String rootURL = getTargetRootURL(TARGET_APPNAME);

      String targetURI = rootURL + MessageReceiverController.ENDPOINT_URI;

      try {
        RequestEntity requestEntity = new RequestEntity(messageBody, HttpMethod.POST,
            new URI(targetURI));
        // 成功后，返回"OK"
        restTemplate.exchange(requestEntity, String.class);
      } catch (URISyntaxException e) {
      }
    });
    return null;
  }

  private String getTargetRootURL(String appName) {

    ServiceInstance targetInstance = getRandomTargetInstance(appName);

    return targetInstance.isSecure() ? "https://" + targetInstance.getHost() + ":" + targetInstance
        .getPort() : "http://" + targetInstance.getHost() + ":" + targetInstance.getPort();
  }

  private ServiceInstance getRandomTargetInstance(String appName) {

    List<ServiceInstance> instancesList = discoveryClient.getInstances(appName);

    int size = instancesList.size();
    int index = new Random().nextInt(size);

    return instancesList.get(index);
  }

}
