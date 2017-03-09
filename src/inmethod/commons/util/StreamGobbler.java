package inmethod.commons.util;


import java.io.*;

/**
 * 利用thread的特性,做出可以隨時抓取input stream傳回來的訊息的工具.
 * @author william chen
 */
class StreamGobbler extends Thread
{
    InputStream is;
    String sName;
    String sInput;
    private static String sEncode = "BIG5";
    private boolean bStop = false;
    private StreamGobbler(){};
    
    public StreamGobbler(InputStream is, String type){
      this(is,type,sEncode);
    }
    
    public StreamGobbler(InputStream is, String sName,String sEncode)
    {
        this.is = is;
        this.sName = sName;
        this.sInput = "";
        StreamGobbler.sEncode = sEncode;
    }
    public String getInput(){
      return sInput;
    }
    
    public void setStopFlag(boolean sBoolean){
    	bStop = sBoolean;
    }
    
    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is,sEncode);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null && !bStop){
                sInput = sInput +"\r\n"+line;
            }
            System.out.println("stop thread name="+sName);
        } catch (IOException ioe){
          ioe.printStackTrace();
        }
    }
}