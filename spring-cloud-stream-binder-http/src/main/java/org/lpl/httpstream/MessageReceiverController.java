package org.lpl.httpstream;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-23 18:25
 **/
@RestController
public class MessageReceiverController implements Controller {

  public static final String ENDPOINT_URI = "/message/receive";

  private MessageChannel inputChannel;

  @PostMapping(ENDPOINT_URI)
  public String receive(HttpServletRequest request) throws IOException {

    // 请求内容
    InputStream inputStream = request.getInputStream();
    // 接收到客户端发送的 HTTP 实体，需要 MessageChannel 回写
    byte[] requestBody = StreamUtils.copyToByteArray(inputStream);
    // 写入到 MessageChannel
    inputChannel.send(new GenericMessage(requestBody));

    return "OK";
  }

  public void setInputChannel(MessageChannel inputChannel) {
    this.inputChannel = inputChannel;
  }

  @Nullable
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    return null;
  }

}
