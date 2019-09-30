package org.lpl.stream.rockermq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 注意：rocketmq install version is the same with maven version
 */
public class RocketMQProducerDemo {

  public static void main(String[] args) throws Exception {
    //Instantiate with a producer group name.
    DefaultMQProducer producer = new
        DefaultMQProducer("lpl-group");

    producer.setNamesrvAddr("localhost:9876");
    //Launch the instance.
    producer.start();

    for (int i = 0; i < 10; i++) {
      //Create a message instance, specifying topic, tag and message body.
      Message msg = new Message("Topictest-lpl" /* Topic */,
          "TagA" /* Tag */,
          ("Hello RocketMQ " +
              i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
      );
      //Call send message to deliver message to one of brokers.
      SendResult sendResult = producer.send(msg);
      System.out.printf("%s%n", sendResult);
    }
    //Shut down once the producer instance is not longer in use.
    producer.shutdown();
  }
}
