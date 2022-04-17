package inmethod.commons.util;
import java.util.*;

/**
 * 將有HTML語法的文字,轉換變成沒有作用的一般文字.
 * @author william chen
 */
public class HTMLConverter {

    private static final String[] htmlCode = new String[256];

    static {
        for (int i = 0; i < 10; i++) {
            htmlCode[i] = "&#00" + i + ";";
        }

        for (int i = 10; i < 32; i++) {
            htmlCode[i] = "&#0" + i + ";";
        }

        for (int i = 32; i < 128; i++) {
            htmlCode[i] = String.valueOf((char)i);
        }

        // Special characters
        htmlCode['\t'] = "";
        htmlCode['\n'] = "<br>"; // 換行
        htmlCode['\"'] = "&quot;"; // "
        htmlCode['&'] = "&amp;"; // &
        htmlCode['<'] = "&lt;"; // <
        htmlCode['>'] = "&gt;"; // >

        for (int i = 128; i < 256; i++) {
            htmlCode[i] = "&#" + i + ";";
        }
    }


    /**
     * convert html tag in String .
     * <pre>
     * ==================================================================
     * example:
     *   HtmlConverter.encode("<html><body>hello World!</body></html>");
     *
     * outout:
     *   &lt;html.........
     * ==================================================================
     * </pre>
     * @param string String before encoded
     * @return String String after encoded
     */
    public static String encode(String string) throws Exception{
      return encode(string,"nouseEncode");
    }

    public static String encode(String string,String noEncode) throws Exception{
        int n = string.length();
        char character;
        StringBuffer buffer = new StringBuffer();
        // loop over all the characters of the String.
        for (int i = 0; i < n; i++) {
            character = string.charAt(i);
            // the Htmlcode of these characters are added to a StringBuffer one by one
            try {
                buffer.append(htmlCode[character]);
            }
            catch(ArrayIndexOutOfBoundsException aioobe) {
                buffer.append(character);
            }
        }
        return buffer.toString().trim();
    }

  /**
   * convert \n to <br>
   * @param nl_string
   * @return String after convert
   */
  public static String nl2br(String nl_string){
    try{
      return nl_string.replace("\n", "").replace("\r", "<br>");
    }catch(Exception ex){
      return nl_string;
    }
  }

  /**
   * convert "" to "&nbsp;"
   * @param s
   * @return String after convert
   */
  public static String space2HtmlTag(String s){
    try{
      return StringConverter.replace(s," ","&nbsp;");
    }catch(Exception ex){
      return s;
    }
  }

  /**
   * convert relative link in html code with absolution link ,
   * only convert <img src= ,<a href= ,backgroudd=
   * @param sHtmlCode source html code
   * @param sURL replace relative with absolute URL
   * @param sPath replace relative with absolute path,must begin with "/"
   */
  public static String convertRelativeLink(String sHtmlCode,String sURL,String sPath) throws Exception{
    Vector aVector = StringConverter.find(sHtmlCode,"href=",">",false);
    String sTemp = null;
    for(int i=0;i<aVector.size();i++){
      sTemp = new String( (String) aVector.get(i));
      System.out.println("sTemp="+sTemp);
      if( find(sTemp,"href=http")!=-1 ||
          find(sTemp,"href='http")!=-1 ||
          find(sTemp,"href=\"http")!=-1 ){
        continue;
      }
      if( find(sTemp,"href=/")!=-1 )
        sTemp = StringConverter.replace(sTemp,"href=","href="+sURL,false);
      else if( find(sTemp,"href='/")!=-1 )
        sTemp = StringConverter.replace(sTemp,"href='","href='"+sURL,false);
      else if( find(sTemp,"href=\"/")!=-1 )
        sTemp = StringConverter.replace(sTemp,"href=\"","href=\""+ sURL,false);
      else if( find(sTemp,"href=\"")!=-1 )
        sTemp = StringConverter.replace(sTemp,"href=\"","href=\"" + sURL + sPath + "/",false);
      else if( find(sTemp,"href='")!=-1 )
        sTemp = StringConverter.replace(sTemp,"href='","href='"+ sURL + sPath + "/",false);
      else if( find(sTemp,"href=")!=-1 )
        sTemp = StringConverter.replace(sTemp,"href=","href=" + sURL + sPath + "/",false);
      sHtmlCode = StringConverter.replace( sHtmlCode, (String) aVector.get(i),sTemp,false);
    }

    aVector = StringConverter.find(sHtmlCode,"src=",">",false);
    for(int i=0;i<aVector.size();i++){
      sTemp = new String( (String) aVector.get(i));
      System.out.println("sTemp="+sTemp);
      if( find(sTemp,"src=http")!=-1 ||
          find(sTemp,"src='http")!=-1 ||
          find(sTemp,"src=\"http")!=-1 ){
        continue;
      }
      if( find(sTemp,"src=/")!=-1 )
        sTemp = StringConverter.replace(sTemp,"src=","src="+sURL,false);
      else if( find(sTemp,"src='/")!=-1 )
        sTemp = StringConverter.replace(sTemp,"src='","src='"+sURL,false);
      else if( find(sTemp,"src=\"/")!=-1 )
        sTemp = StringConverter.replace(sTemp,"src=\"","src=\""+ sURL,false);
      else if( find(sTemp,"src=\"")!=-1 )
        sTemp = StringConverter.replace(sTemp,"src=\"","src=\"" + sURL + sPath + "/",false);
      else if( find(sTemp,"src='")!=-1 )
        sTemp = StringConverter.replace(sTemp,"src='","src='"+ sURL + sPath + "/",false);
      else if( find(sTemp,"src=")!=-1 )
        sTemp = StringConverter.replace(sTemp,"src=","src=" + sURL + sPath + "/",false);
      sHtmlCode = StringConverter.replace( sHtmlCode, (String) aVector.get(i),sTemp,false);
    }

    aVector = StringConverter.find(sHtmlCode,"background=",">",false);
    for(int i=0;i<aVector.size();i++){
      sTemp = new String( (String) aVector.get(i));
      System.out.println("background="+sTemp);
      if( find(sTemp,"background=http")!=-1 ||
          find(sTemp,"background='http")!=-1 ||
          find(sTemp,"background=\"http")!=-1 ){
        continue;
      }
      if( find(sTemp,"background=/")!=-1 )
        sTemp = StringConverter.replace(sTemp,"background=","background="+sURL,false);
      else if( find(sTemp,"background='/")!=-1 )
        sTemp = StringConverter.replace(sTemp,"background='","background='"+sURL,false);
      else if( find(sTemp,"background=\"/")!=-1 )
        sTemp = StringConverter.replace(sTemp,"background=\"","background=\""+ sURL,false);
      else if( find(sTemp,"background=\"")!=-1 )
        sTemp = StringConverter.replace(sTemp,"background=\"","background=\"" + sURL + sPath + "/",false);
      else if( find(sTemp,"background='")!=-1 )
        sTemp = StringConverter.replace(sTemp,"background='","background='"+ sURL + sPath + "/",false);
      else if( find(sTemp,"background=")!=-1 )
        sTemp = StringConverter.replace(sTemp,"background=","background=" + sURL + sPath + "/",false);
      sHtmlCode = StringConverter.replace( sHtmlCode, (String) aVector.get(i),sTemp,false);
    }

    aVector = StringConverter.find(sHtmlCode,"action=",">",false);
    for(int i=0;i<aVector.size();i++){
      sTemp = new String( (String) aVector.get(i));
      System.out.println("action="+sTemp);
      if( find(sTemp,"action=http")!=-1 ||
          find(sTemp,"action='http")!=-1 ||
          find(sTemp,"action=\"http")!=-1 ){
        continue;
      }
      if( find(sTemp,"action=/")!=-1 )
        sTemp = StringConverter.replace(sTemp,"action=","action="+sURL,false);
      else if( find(sTemp,"action='/")!=-1 )
        sTemp = StringConverter.replace(sTemp,"action='","action='"+sURL,false);
      else if( find(sTemp,"action=\"/")!=-1 )
        sTemp = StringConverter.replace(sTemp,"action=\"","action=\""+ sURL,false);
      else if( find(sTemp,"action=\"")!=-1 )
        sTemp = StringConverter.replace(sTemp,"action=\"","action=\"" + sURL + sPath + "/",false);
      else if( find(sTemp,"action='")!=-1 )
        sTemp = StringConverter.replace(sTemp,"action='","action='"+ sURL + sPath + "/",false);
      else if( find(sTemp,"action=")!=-1 )
        sTemp = StringConverter.replace(sTemp,"action=","action=" + sURL + sPath + "/",false);
      sHtmlCode = StringConverter.replace( sHtmlCode, (String) aVector.get(i),sTemp,false);
    }

    aVector = StringConverter.find(sHtmlCode,"<base ",">",false);
    for(int i=0;i<aVector.size();i++){
      sHtmlCode = StringConverter.replace( sHtmlCode, (String) aVector.get(i)," ",false);
    }

    return sHtmlCode;
  }

  private static int find(String sSource,String sBegin) throws Exception{
    String sSourceClone = new String(sSource).toUpperCase();
    String sBeginClone = new String(sBegin).toUpperCase();
    return sSourceClone.indexOf(sBeginClone);
  }

  /**
   * main example.
   * <pre>
   * System.out.println(HtmlConverter.convert("<html><body>許功蓋</body></html>"));
   * </pre>
   */
  public static void main(String[] a){
    try{
      System.out.println(HTMLConverter.encode("<html><body>許功蓋</body></html>"));
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }
}