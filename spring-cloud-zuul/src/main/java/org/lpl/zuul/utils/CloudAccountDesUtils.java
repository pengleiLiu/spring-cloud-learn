package org.lpl.zuul.utils;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-06 17:39
 **/
public class CloudAccountDesUtils {

  public static byte[] TripleDesEncrypt(byte[] content, byte[] key) throws Exception {
    byte[] icv = new byte[8];
    System.arraycopy(key, 0, icv, 0, 8);
    return TripleDesEncrypt(content, key, icv);
  }

  protected static byte[] TripleDesEncrypt(byte[] content, byte[] key, byte[] icv) throws
      Exception {
    final SecretKey secretKey = new SecretKeySpec(key, "DESede");
    final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
    final IvParameterSpec iv = new IvParameterSpec(icv);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
    return cipher.doFinal(content);
  }

  public static byte[] TripleDesDecrypt(byte[] content, byte[] key) throws Exception {
    byte[] icv = new byte[8];
    System.arraycopy(key, 0, icv, 0, 8);
    return TripleDesDecrypt(content, key, icv);
  }


  public static String sha256_HMAC(String message, String secret) {
    String hash = "";
    try {
      Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
      SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
      sha256_HMAC.init(secret_key);
      byte[] bytes = sha256_HMAC.doFinal(message.getBytes("utf-8"));
      hash = byteArrayToHexString(bytes);
    } catch (Exception e) {
      System.out.println("Error HmacSHA256 ===========" + e.getMessage());
    }
    return hash;
  }

  /**
   * 将加密后的字节数组转换成字符串
   *
   * @param b 字节数组
   * @return 字符串
   */
  private static String byteArrayToHexString(byte[] b) {
    StringBuilder hs = new StringBuilder();
    String stmp;
    for (int n = 0; b != null && n < b.length; n++) {
      stmp = Integer.toHexString(b[n] & 0XFF);
      if (stmp.length() == 1) {
        hs.append('0');
      }
      hs.append(stmp);
    }
    return hs.toString().toLowerCase();
  }


  protected static byte[] TripleDesDecrypt(byte[] content, byte[] key, byte[] icv) throws
      Exception {
    final SecretKey secretKey = new SecretKeySpec(key, "DESede");
    final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
    final IvParameterSpec iv = new IvParameterSpec(icv);
    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
    return cipher.doFinal(content);
  }

  public static void main(String[] args) {
    String s = sha256_HMAC(
        "data=0eUw2Nk2isX+IRlttM7eKomZCAfJlocPw2lG4uVrc0rOqzK85qW9tKshFrFmY8mrmD34UO6UBRpOaG4dKnENniZT3l0KSg/DFOiUu+YbJdxbpPiJs77WYIO1EZ5A70Wxxkc7u3rR2SQfqMwmgP/0yEPeqMqYMcDNgy/CUI5sBNagdue0L/UknhYSzbzxCNlyA3pyy84IETNfQmiQLHbkLwD3TvfT/rZdYu9PV2QpNGEFxe6PjcdFFU8gbIeuImjZyJIAU8umpMiMpx3xNqnraie8e7+5dzrS&mess=12313&timestamp=123457&key=78f9b4fad3481fbce1df0b30eee58577",
        "78f9b4fad3481fbce1df0b30eee58577");

    System.out.println(s);
  }
}
