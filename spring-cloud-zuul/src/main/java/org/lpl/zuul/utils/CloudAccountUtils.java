package org.lpl.zuul.utils;

import com.alibaba.fastjson.JSONObject;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.codec.binary.Base64;

/**
 * 云账户接口请求组件
 *
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-06 17:38
 **/
public class CloudAccountUtils {


  private static final String SERVER_ROOT_URL = "https://api-jiesuan.yunzhanghu.com";

  private static final String IDCARD_AUTH_URL = "/authentication/verify-id";

  private static final String BANKCARD_AUTH_URL = "/authentication/verify-bankcard-three-factor";

  private static final String SIGN_TYPE = "sha256";

  private static final String HEADER_KEY_DEALER_ID = "dealer-id";

  private static final String HEADER_KEY_REQUEST_ID = "request-id";

  //  /**
//   * 身份证号不合法
//   */
//  private static final String VERFITY_CODE_IDCARD_ERROR = "10102";
//  /**
//   * 姓名输入有误
//   */
//  private static final String VERFITY_CODE_NAME_ERROR = "10103";
//  /**
//   * 银行卡信息校验失败
//   */
//  private static final String VERFITY_CODE_BANK_ERROR = "10104";
//  /**
//   * 姓名银行卡不匹配
//   */
//  private static final String VERFITY_CODE_NAME_IDCARD_MISMATCH = "10105";
//  /**
//   * 身份证号与银行卡号不匹配
//   */
//  private static final String VERFITY_CODE_NAME_BANK_MISMATCH = "10106";
//  /**
//   * 姓名/身份证号/银行卡号不匹配
//   */
//  private static final String VERFITY_CODE_NAME_IDCARD_BANK_ERROR = "10108";

  private static final String dealer_id = "";

  private static final String thirdsKey = "";
  private static final String appKey = "";

  private static final int TIMEOUT = 10;

  private static final OkHttpClient client = new OkHttpClient()
      .newBuilder()
      .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
      .readTimeout(TIMEOUT, TimeUnit.SECONDS)
      .build();

  /**
   * 实名认证接口
   */
  public static void idCardAuth() throws Exception {

    String idCard = "321002197305030914";
    String userName = "张三";

    Map<String, String> paramsMap = new HashMap<>();
    paramsMap.put("id_card", idCard);
    paramsMap.put("real_name", userName);

    FormBody formBody = createFormBody(JSONObject.toJSONString(paramsMap));

    Headers headers = new Builder()
        .add("dealer-id", dealer_id)
        .add("request-id", UUID.randomUUID().toString().replace("-", ""))
        .build();

    Request request = new Request.Builder()
        .headers(headers)
        .url(SERVER_ROOT_URL + IDCARD_AUTH_URL)
        .post(formBody)
        .build();

    Response execute = client.newCall(request).execute();

    System.out.println(execute.body().string());

  }

  /**
   * 银行卡三要素校验
   */
  public static void bankCardAuth() throws Exception {
    String idCard = "132437197503020220";
    String userName = "张玉娜";
    String bankCard = "6217681806068907";

    Map<String, String> paramsMap = new HashMap<>();
    paramsMap.put("id_card", idCard);
    paramsMap.put("real_name", userName);
    paramsMap.put("card_no", bankCard);

    FormBody formBody = createFormBody(JSONObject.toJSONString(paramsMap));

    String requestId = UUID.randomUUID().toString().replace("-", "");
    Headers headers = new Builder()
        .add(HEADER_KEY_DEALER_ID, dealer_id)
        .add(HEADER_KEY_REQUEST_ID, requestId)
        .build();

    Request request = new Request.Builder()
        .headers(headers)
        .url(SERVER_ROOT_URL + BANKCARD_AUTH_URL)
        .post(formBody)
        .build();

    Response execute = client.newCall(request).execute();
    ResponseBody body = execute.body();
    System.out.println(execute.body().string());
  }

  /**
   * 构建formBody
   *
   * @param requestParams 请求参数
   * @return 加密后的formBody
   */
  public static FormBody createFormBody(String requestParams) throws Exception {

    byte[] content = requestParams.getBytes(StandardCharsets.UTF_8);
    byte[] enc = CloudAccountDesUtils
        .TripleDesEncrypt(content, thirdsKey.getBytes(StandardCharsets.UTF_8));
    byte[] enc64 = Base64.encodeBase64(enc);

    String thirdDesContent = new String(enc64);

    long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    int mess = new Random().nextInt(10000);

    String pre = "data=" + thirdDesContent + "&mess=" + mess +
        "&timestamp=" + timestamp + "&key=" + appKey;

    String sign = CloudAccountDesUtils.sha256_HMAC(pre, appKey);

    FormBody formBody = new FormBody.Builder()
        .add("data", thirdDesContent)
        .add("mess", String.valueOf(mess))
        .add("timestamp", String.valueOf(timestamp))
        .add("sign", sign)
        .add("sign_type", SIGN_TYPE)
        .build();

    return formBody;
  }

  public static void main(String[] args) throws Exception {
    //CloudAccountUtils.idCardAuth();

    CloudAccountUtils.bankCardAuth();
  }


}
