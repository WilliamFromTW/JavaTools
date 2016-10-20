package inmethod.commons.util;

/**
 * 將訊息以"MIME"格式編碼.
 * @author william chen
 */
public class MIMEConverter{

  public MIMEConverter() {
  }
  private static String sEncode = "BIG5";

  /**
   * override Converter.convert
   * must use Java Mail api
   * @param string url string
   * @return string encoded with url format
   */
  public static String encode(String string)  throws Exception{
    return encode(string,sEncode);
  }

  /**
   * override Converter.convert
   * must use Java Mail api
   * @param string url string
   * @param sEncode encode
   * @return string encoded with url format
   */
  public static String encode(String string,String sEncode)  throws Exception{
    return javax.mail.internet.MimeUtility.encodeText(string,sEncode,"B");
  }

  public static void main(String[] args) {
    MIMEConverter MIMEConverter1 = new MIMEConverter();
  }
}