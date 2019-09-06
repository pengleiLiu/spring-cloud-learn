package org.lpl.zuul.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.codec.binary.Base64;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-06 17:38
 **/
public class CloudAccountUtils {


  private static final String serverRoot = "https://api-jiesuan.yunzhanghu.com";

  private static final String url = "/authentication/verify-id";

  private static final int TIMEOUT = 5;

  private static final OkHttpClient client = new OkHttpClient()
      .newBuilder()
      .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
      .readTimeout(TIMEOUT, TimeUnit.SECONDS)
      .build();


  public static void idCardAuth() throws Exception {

    String thirdsKey = "RmuIwVUDKV3zuTp07xR4e60g";
    String appKey = "6C25pA3d9WLo91z5ppAxEv1oIj3DSG1I";

    String idCard = "130684199402142272";
    String userName = "刘朋磊";

    Map<String, String> paramsMap = new HashMap<String, String>();
    paramsMap.put("id_card", idCard);
    paramsMap.put("real_name", userName);

    byte[] content = JSONObject.toJSONString(paramsMap).getBytes("UTF-8");

    byte[] enc = CloudAccountDesUtils.TripleDesDecrypt(thirdsKey.getBytes("UTF-8"), content);

    byte[] enc64 = Base64.encodeBase64(enc);

    MediaType jsonType = MediaType.parse("application/json; charset=utf-8");

    Request request = new Request.Builder()
        .header("dealer-id", "24459062")
        .header("request-id", UUID.randomUUID().toString().replace("-", ""))
        .url(url)
        .post(RequestBody.create(jsonType, enc64))
        .build();

    Response execute = client.newCall(request).execute();
    System.out.println(execute);

  }

}
