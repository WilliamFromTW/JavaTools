package inmethod.commons.util;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;

/**
 * 將字串以"MD5"或是"SHA"編碼.
 * @author william chen
 */
public class Crypt {


  public static void main(String a[]){
    try{

      String s3DES = "DESede";
      // 設定欲加密的資料
      String sData = "ilovefromtw";
      String sData2 = null ; // 預備存放解密之後的資料,然後與原始資料比對是否解密成功

      // 產生3DES的Secret key
      KeyGenerator keygen = KeyGenerator.getInstance(s3DES);
      SecretKey deskey = keygen.generateKey();

      // 使用Secret key 將資料加密之後取得加密後的資料
      Cipher c = Cipher.getInstance(s3DES);
      c.init(Cipher.ENCRYPT_MODE,deskey);
      byte[] cipherByte=c.doFinal(sData.getBytes());

      // 使用Secret key 將加密的資料解開
      Cipher c1 = Cipher.getInstance(s3DES);
      c1.init( Cipher.DECRYPT_MODE, deskey);
      sData2 = new String( c1.doFinal( cipherByte)) ;

      System.out.println( "原始資料 = " + sData);
      System.out.println( "解密之後的資料 = " + sData2);
/*
      KeyGenerator keygen = KeyGenerator.getInstance("DESede");
      SecretKey deskey = keygen.generateKey();
      System.out.println("3des key=" + asHex( deskey.getEncoded() ));
      String s3DesEncode =  asHex( deskey.getEncoded() );
      Cipher c = Cipher.getInstance("DESede");
      c.init(Cipher.ENCRYPT_MODE,deskey);
      byte[] cipherByte=c.doFinal("llll".getBytes());
      System.out.println("3des value=" +asHex( cipherByte ));

      SecretKeySpec kk= new SecretKeySpec( asByte(s3DesEncode)  ,"DESede");

      Cipher c1 = Cipher.getInstance("DESede");
      c1.init( Cipher.DECRYPT_MODE, kk);
      System.out.println("decode = " + new String( c1.doFinal( cipherByte)) );
*/
      String sEncodeData = "ddd";
      String sSKEncode = Crypt.get3DESSecretKeyEncode();
      String s3DesEncode =  Crypt.get3DESEncode( sSKEncode,sEncodeData);
      String s3DesDecode = Crypt.get3DESDecode( sSKEncode, s3DesEncode);
      System.out.println("encode data = " + sEncodeData );
      System.out.println( "Secret key encode = " + sSKEncode);
      System.out.println( "3 DES Encode  = " + s3DesEncode);
      System.out.println( "3 DES Decode = " + s3DesDecode);

      System.out.println( "source = dd,md5=" + MD5("dd"));
   }catch(Exception ex){
     ex.printStackTrace();
   }

  }

  public static void main2(String a[]){
    try{
      String sTemp = null;
      sTemp = Crypt.DESEncode("asdf","widlliam");
      System.out.println( sTemp+ ",length=" + sTemp.length());
      System.out.println( Crypt.DESDecode("asdf",sTemp) );

    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

  public Crypt() {
  }

  /**
   * 取得3DES 的 Secret key (然後取崆ecret key的Encode)
   * @return String Secret Key Encode
   */
  public static String get3DESSecretKeyEncode() throws Exception {

    KeyGenerator keygen = KeyGenerator.getInstance("DESede");
    SecretKey deskey = keygen.generateKey();
    System.out.println("3des secret key encode =" + asHex( deskey.getEncoded() ));
    return asHex( deskey.getEncoded() );

  }

  /**
   * 取得3DES 的 Secret key (然後取崆ecret key的Encode)
   * @return String Secret Key Encode
   */
  public static String get3DESEncode(String sSecretKeyEncode,String sData) throws Exception {

    SecretKeySpec kk= new SecretKeySpec( asByte(sSecretKeyEncode)  ,"DESede");
    Cipher c = Cipher.getInstance("DESede");
    c.init(Cipher.ENCRYPT_MODE,kk);
    byte[] cipherByte=c.doFinal(sData.getBytes());
    System.out.println("3des value=" +asHex( cipherByte ));
    return  asHex( cipherByte );

  }

  public static String get3DESDecode(String sSecretKeyEncode,String s3DESEncode) throws Exception{

    SecretKeySpec kk= new SecretKeySpec( asByte(sSecretKeyEncode)  ,"DESede");
    Cipher c1 = Cipher.getInstance("DESede");
    c1.init( Cipher.DECRYPT_MODE, kk);
    System.out.println("decode = " + new String( c1.doFinal( asByte(s3DESEncode))) );
    return new String( c1.doFinal( asByte(s3DESEncode)));
  }

  public static String DESEncode(String sEncodeKey,String sData) throws Exception{
    //生成密鑰
    KeyGenerator keygen = KeyGenerator.getInstance("DES");
    SecretKeySpec kk= new SecretKeySpec(get8BitsKey(sEncodeKey).getBytes(),"DES");
    SecretKey deskey = keygen.generateKey();
    //加密
    System.out.println("加密前的資料:"+sData+",key="+sEncodeKey);

    Cipher c1 = Cipher.getInstance("DES");
    c1.init(Cipher.ENCRYPT_MODE,kk);
    byte[] cipherByte=c1.doFinal(sData.getBytes());
    return asHex( cipherByte );
  }

  public static String DESDecode(String sEncodeKey,String sEncodeData) throws Exception{
    SecretKeySpec kk= new SecretKeySpec(get8BitsKey(sEncodeKey).getBytes(),"DES");
    //解密
    Cipher c1 = Cipher.getInstance("DES");
    c1.init(Cipher.DECRYPT_MODE,kk);
    byte[] clearByte=c1.doFinal(asByte(sEncodeData)  );
    System.out.println("解密後的信息:"+(new String(clearByte))+",Key="+sEncodeKey);
    return new String(clearByte);

  }

  /**
   * encode Sting with md5
   * @param strParam  String before encoded
   * @return String after encoded,  null: encode fail
   */
  public static String MD5(String strParam){
    byte[] aByte = null;

    try{
      MessageDigest aMD = MessageDigest.getInstance("MD5");
      aMD.update(strParam.getBytes());
      aByte = aMD.digest();
      return asHex(aByte);
    }catch(Exception ex){ex.printStackTrace();};
    return null;
  }

  /**
   * Encode string with SHA
   * @param strParam String before encoded
   * @return String after encoded,  null: encode fail
   */
  public static String SHA(String strParam){
    byte[] aByte = null;
    try{
      MessageDigest aMD = MessageDigest.getInstance("SHA");
      aMD.update(strParam.getBytes());
      aByte = aMD.digest();
    }catch(Exception ex){ ex.printStackTrace();};
    return null;
  }

  /**
   * Bytes transfer to  Hex String.
   * ex:
   * \u000D -> 0D
   */
  private static String asHex (byte hash[]) {
      StringBuffer buf = new StringBuffer(hash.length * 2);
      int i;
      for (i = 0; i < hash.length; i++) {
        if ( ((int) hash[i] & 0xff) < 0x10)
          buf.append("0");
          buf.append(Long.toString((int) hash[i] & 0xff, 16));
      }
      return buf.toString().toUpperCase();
  }

  private static byte[] asByte(String strhex){
    if(strhex==null) return null;
    int l = strhex.length();
    if(l %2 ==1) return null;
    byte[] b = new byte[l/2];
    for(int i = 0 ; i < l/2 ;i++){
      b[i] = (byte)Integer.parseInt(strhex.substring(i *2,i*2 +2),16);
    }
    return b;
  }

  /**
   * If key length is less than 8, this will padding fit 8 bits
   * @param sKey String key
   * @exception if sKey is null
   */
  private static String get8BitsKey(String sKey) throws Exception{
    String sReturn = sKey;
    if( sKey.length()>=8 )
      return sKey.substring(0,8);
    for(int i=sKey.length() ;i<8;i++){
      sReturn = sReturn + "d";
    }

    return sReturn;

  }

}