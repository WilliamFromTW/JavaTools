// TestCrypt.java
package test;
import inmethod.commons.util.Crypt;

/**
 * 測試 Crypt .
 * @see Crypt
 */
public class TestCrypt{
  public static void main(String a[]){
    try{
      String sEncodeData = "data";
      // 必須呼叫Crypt.get3DESSecretKeyEncode()取得密鑰,此密鑰可以解開加密的資料
      String sSKEncode = Crypt.get3DESSecretKeyEncode();
      // 將資料加密
      String s3DesEncode =  Crypt.get3DESEncode( sSKEncode,sEncodeData);
      // 將加密的資料解密
      String s3DesDecode = Crypt.get3DESDecode( sSKEncode, s3DesEncode);

      System.out.println("預備加密的資料 = " + sEncodeData );
      System.out.println("加密的密碼 = " + sSKEncode);

      System.out.println( "經過3Des加密後的資料  = " + s3DesEncode);
      System.out.println( "3Des加密資料解開後 = " + s3DesDecode);

      System.out.println( "做md5編碼前的資料 = dd ,md5編碼後 =" + Crypt.MD5("dd"));
   }catch(Exception ex){
     ex.printStackTrace();
   }

  }

}