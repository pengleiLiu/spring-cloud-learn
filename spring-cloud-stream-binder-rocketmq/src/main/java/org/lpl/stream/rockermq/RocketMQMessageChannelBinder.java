package org.lpl.stream.rockermq;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-30 15:26
 **/
public class RocketMQMessageChannelBinder implements
    Binder<MessageChannel, ConsumerProperties, ProducerProperties> {

  private static final String GROUP = "stream-test-group";

  private static final String TOPIC = "STREAM_TEST_TOPIC";

  private static final String TAG = "STREAM_TAG";

  private static final String ADDRESS = "localhost:9876";

  @Override
  public Binding<MessageChannel> bindConsumer(String name, String group,
      MessageChannel inputChannel, ConsumerProperties consumerProperties) {

    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GROUP);
    consumer.setNamesrvAddr(ADDRESS);
    consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

    try {
      consumer.subscribe(TOPIC, TAG);
      consumer.registerMessageListener(new MessageListenerConcurrently() {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
            ConsumeConcurrentlyContext context) {

          System.out.printf(Thread.currentThread().getName()
              + " Receive New Messages: " + msgs + "%n");

          msgs.forEach(msg -> {
            byte[] body = msg.getBody();
            // 发送消息到 消息管道
            inputChannel.send(new GenericMessage<Object>(body));
          });
          return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
      });

      consumer.start();
    } catch (Exception e) {
      e.getMessage();
    }

    return () -> {
      System.out.println("consumer shutdown...");
      consumer.shutdown();
    };
  }

  @Override
  public Binding<MessageChannel> bindProducer(String name, MessageChannel outputChannel,
      ProducerProperties producerProperties) {

    DefaultMQProducer producer = new DefaultMQProducer(GROUP);

    producer.setNamesrvAddr(ADDRESS);

    try {
      producer.start();
      SubscribableChannel subscribableChannel = (SubscribableChannel) outputChannel;

      subscribableChannel.subscribe(message -> {
        Object messageBody = message.getPayload();
        Message mqMessage = new Message();
        mqMessage.setTopic(TOPIC);
        mqMessage.setTags(TAG);
        try {
          mqMessage.setBody(serialize(messageBody));
          producer.send(mqMessage);
        } catch (Exception e) {
          e.getMessage();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }

    return () -> {
      System.out.println("producer shutdown...");
      producer.shutdown();
    };
  }

  private byte[] serialize(Object serializable) throws Exception {

    if (serializable instanceof byte[]) {
      return (byte[]) serializable;
    }

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    //通过 Java 序列化 将 Object 写入字节流
    objectOutputStream.writeObject(serializable);
    return outputStream.toByteArray();
  }
}
