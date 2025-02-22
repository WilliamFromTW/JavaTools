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
      String sData = "data";
      String sSKEncode = "123456781234567812345678";
      // 將資料加密
      String s3DesEncode =  Crypt.Encrypt3DesCbcPKCS7Padding( sSKEncode,sData);
      // 將加密的資料解密
     String s3DesDecode = Crypt.Decrypt3DesCbcPKCS7Padding(  sSKEncode,s3DesEncode);

      System.out.println("預備加密的資料 = " + sData );
      System.out.println("加密的密碼 = " + sSKEncode);

      System.out.println( "經過3Des加密後的資料  = " + s3DesEncode);
      System.out.println( "3Des加密資料解開後 = " + s3DesDecode);

      System.out.println( "做md5編碼前的資料 = dd ,md5編碼後 =" + Crypt.MD5("dd"));
   }catch(Exception ex){
     ex.printStackTrace();
   }

  }

}