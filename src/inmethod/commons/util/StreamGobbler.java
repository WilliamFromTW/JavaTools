package inmethod.commons.util;


import java.io.*;

/**
 * 利用thread的特性,做出可以隨時抓取input stream傳回來的訊息的工具.
 * @author william chen
 */
class StreamGobbler extends Thread
{
    InputStream is;
    String type;
    String sInput;
    private static String sEncode = "BIG5";
    StreamGobbler(InputStream is, String type){
      this(is,type,sEncode);
    }
    StreamGobbler(InputStream is, String type,String sEncode)
    {
        this.is = is;
        this.type = type;
        this.sInput = "";
        StreamGobbler.sEncode = sEncode;
    }
    public String getInput(){
      return sInput;
    }
    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is,sEncode);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null){
                sInput += line;
            }
        } catch (IOException ioe){
          ioe.printStackTrace();
        }
    }
}