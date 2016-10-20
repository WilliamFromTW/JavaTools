package inmethod.commons.util;
import java.io.*;

/**
 * 透過作業系統的shell執行命令,會因為OS不同,有不同的shell.
 * @author william chen
 */
public class ExeShellCmd{

  public static final String WINDOWS = "WINDOWS";
  public static final String WINDOWS_95 = "WINDOWS 95";
  public static final String WINDOWS_98 = "WINDOWS 98";
  public static final String WINDOWS_NT = "WINDOWS NT";
  public static final String WINDOWS_2K = "WINDOWS 2000";
  public static final String WINDOWS_XP = "WINDOWS XP";
  public static final String WINDOWS_7 = "WINDOWS 7";
  public static final String LINUX = "LINUX";
  public static final String SOLARIS = "SOLARIS";
  public static final String AIX = "AIX";
  public static final String FREEBSD = "FREEBSD";
  public static final String IRIX = "IRIX";
  public static final String HP_UX = "HP-UX";

  private static String sEncode = "BIG5";

   public static void setInputEncode(String sEnc){
     sEncode = sEnc;
   }

  /**
   * AutoDetecting OS and run the correct shell command.
   */
  public void exec(String sCmd[]){
    String sOS = System.getProperty( "os.name" ).toUpperCase();
    System.out.println(sOS);
    if( sOS.indexOf(WINDOWS)!=-1 || sOS.equalsIgnoreCase(WINDOWS_7) || sOS.equalsIgnoreCase(WINDOWS_2K) || sOS.equalsIgnoreCase(WINDOWS_NT) || sOS.equalsIgnoreCase(WINDOWS_XP) )
      execInWinNT(sCmd);
    if( sOS.equalsIgnoreCase(WINDOWS_95) || sOS.equalsIgnoreCase(WINDOWS_98) )
      execInWin9x(sCmd);
    if( sOS.equalsIgnoreCase(LINUX) || sOS.equalsIgnoreCase(FREEBSD) || sOS.equalsIgnoreCase(AIX) || sOS.equalsIgnoreCase(IRIX) || sOS.equalsIgnoreCase(SOLARIS) || sOS.equalsIgnoreCase(HP_UX) )
      execInUNIX(sCmd);
  }

  /**
   * run in win9x.
   */
  private void execInWin9x(String sCmd[]){
    try{
      Runtime aRT = Runtime.getRuntime(); // Runtime.getRuntime();
      /* Spawn a shell sub-process */
      Process aProc = aRT.exec( "command.com" );
      exec(aProc,sCmd);
    }catch(IOException ex){ 
      ex.printStackTrace();
    }
  }

  /**
   * run in winNT,win2000.
   */
  private void execInWinNT(String sCmd[]){
    try{
      Runtime aRT = Runtime.getRuntime(); // Runtime.getRuntime();
      /* Spawn a shell sub-process */
      Process aProc = aRT.exec( "cmd.exe" );
      exec(aProc,sCmd);
    }catch(IOException ex){
      ex.printStackTrace();
    }
  }

  /**
   *   run in UNIX.
   */
  private void execInUNIX(String sCmd[]){
    try{
      Runtime aRT = Runtime.getRuntime(); // Runtime.getRuntime();
      /* Spawn a shell sub-process */
      Process aProc = aRT.exec( "sh" );
      exec(aProc,sCmd);
    }catch(IOException ex){
      ex.printStackTrace();
    }
  }

  /**
   * General Exe method.
   */
  private void exec(Process aProc,String sCmd[]){
    try{
      /* Get the 'proc' stdout for talking to the shell */
      PrintWriter sot = new PrintWriter( new OutputStreamWriter(
                        aProc.getOutputStream() ) );
      // any error message?
      StreamGobbler errorGobbler = new StreamGobbler(aProc.getErrorStream(), "ERROR",sEncode);
      // any output?
      StreamGobbler outputGobbler = new StreamGobbler(aProc.getInputStream(), "OUTPUT",sEncode);

      for(int i=0;i<sCmd.length;i++){
        sot.println(sCmd[i]);
        sot.flush();
      }
      /* Get back the response of the echo command and/or any error
      messages... */
      sot.close();
      outputGobbler.run();
      errorGobbler.run();
      /* Close the communication channels and terminate the shell */
      int exitVal = aProc.waitFor();


      aProc.getErrorStream().close();
      aProc.getInputStream().close();      
      return;
    }catch(Exception ioex){
      ioex.printStackTrace();
    }
  }

  /**
   * main example.
   * <pre>
   * Example
   *  ExeShellCmd oShellCmd = new ExeShellCmd();
   *  // dir
   *  String[] aCmd=new String[2];
   *  aCmd[0] = "dir c:\\temp";
   *  aCmd[1] = "dir d:\\temp";
   *  oShellCmd.execInWinNT(aCmd);
   *  </pre>
   */
  public static void main(String[] args) {
     ExeShellCmd oShellCmd = new ExeShellCmd();
     // dir
     String[] aCmd=new String[2];
//     aCmd[0] = "mkdir c:\\123";
     aCmd[0] = "dir c:\\temp";
     aCmd[1] = "echo hello";
     oShellCmd.exec(aCmd);
     System.out.println("test");
  }
}