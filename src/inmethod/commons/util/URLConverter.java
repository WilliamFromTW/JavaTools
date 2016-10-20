package inmethod.commons.util;
import java.util.BitSet;
import java.io.*;

/**
 * 將url的指令預先編成正確的url專用碼.
 * @author william chen
 */
public class URLConverter{

  public URLConverter() {
  }

  private static String sEncode = "BIG5";

  /**
   * override Converter.convert
   * @param string  url string
   * @return string encoded with url format
   */
  public static String encode(String string)  throws Exception{
    return encode(string,sEncode);
  }

  /**
   * override Converter.encode
   * @param string  url string
   * @param sEncode encode
   * @return string encoded with url format
   */
  public static String encode(String string,String sEncode)  throws Exception{
    return new_encode(string,sEncode);
    /** if using jsdk version 1.4 please using the following code */
    //return java.net.URLEncoder.encode(string,sEncode);
  }

  private static String new_encode(String s, String enc) throws UnsupportedEncodingException {
    int caseDiff = ('a' - 'A');
    BitSet dontNeedEncoding;
    {
      dontNeedEncoding = new BitSet(256);
      int i;
      for (i = 'a'; i <= 'z'; i++) {
        dontNeedEncoding.set(i);
      }
      for (i = 'A'; i <= 'Z'; i++) {
        dontNeedEncoding.set(i);
      }
      for (i = '0'; i <= '9'; i++) {
        dontNeedEncoding.set(i);
      }
      dontNeedEncoding.set(' ');
      /* encoding a space to a + is done in the encode() method */
      dontNeedEncoding.set('-');
      dontNeedEncoding.set('_');
      dontNeedEncoding.set('.');
      dontNeedEncoding.set('*');
    };
    boolean needToChange = false;
    boolean wroteUnencodedChar = false;
    int maxBytesPerChar = 10; // rather arbitrary limit, but safe for now
    StringBuffer out = new StringBuffer(s.length());
    ByteArrayOutputStream buf = new ByteArrayOutputStream(maxBytesPerChar);

    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(buf, enc));

    for (int i = 0; i < s.length(); i++) {
      int c = (int) s.charAt(i);
      //System.out.println("Examining character: " + c);
      if (dontNeedEncoding.get(c)) {
        if (c == ' ') {
          c = '+';
          needToChange = true;
        }
        //System.out.println("Storing: " + c);
        out.append((char)c);
        wroteUnencodedChar = true;
      }
      else {
        // convert to external encoding before hex conversion
        try {
          if (wroteUnencodedChar) { // Fix for 4407610
            writer = new BufferedWriter(new OutputStreamWriter(buf, enc));
            wroteUnencodedChar = false;
          }
          writer.write(c);
          /*
          * If this character represents the start of a Unicode
          * surrogate pair, then pass in two characters. It's not
          * clear what should be done if a bytes reserved in the
          * surrogate pairs range occurs outside of a legal
          * surrogate pair. For now, just treat it as if it were
          * any other character.
          */
          if (c >= 0xD800 && c <= 0xDBFF) {
            /*
            System.out.println(Integer.toHexString(c)
            + " is high surrogate");
            */
            if ( (i+1) < s.length()) {
              int d = (int) s.charAt(i+1);
              /*
              System.out.println("\tExamining "
              + Integer.toHexString(d));
              */
              if (d >= 0xDC00 && d <= 0xDFFF) {
                /*
                System.out.println("\t"
                + Integer.toHexString(d)
                + " is low surrogate");
                */
                writer.write(d);
               i++;
              }
            }
          }
          writer.flush();
        } catch(IOException e) {
          buf.reset();
          continue;
        }
        byte[] ba = buf.toByteArray();
        for (int j = 0; j < ba.length; j++) {
          out.append('%');
          char ch = Character.forDigit((ba[j] >> 4) & 0xF, 16);
          // converting to use uppercase letter as part of
          // the hex value if ch is a letter.
          if (Character.isLetter(ch)) {
            ch -= caseDiff;
          }
          out.append(ch);
          ch = Character.forDigit(ba[j] & 0xF, 16);
          if (Character.isLetter(ch)) {
            ch -= caseDiff;
          }
          out.append(ch);
        }
        buf.reset();
        needToChange = true;
      }
    }
    return (needToChange? out.toString() : s);
  }

  public static void main(String[] args) {
    URLConverter URLConverter1 = new URLConverter();
  }
}