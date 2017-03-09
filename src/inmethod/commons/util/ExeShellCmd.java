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
  public static final String WINDOWS_10 = "WINDOWS 10";
  public static final String LINUX = "LINUX";
  public static final String SOLARIS = "SOLARIS";
  public static final String AIX = "AIX";
  public static final String FREEBSD = "FREEBSD";
  public static final String IRIX = "IRIX";
  public static final String HP_UX = "HP-UX";

  private static String sEncode = "BIG5";
  private String sNormalMessage = "";
  private String sErrorMessge = "";
  
   public static void setInputEncode(String sEnc){
     sEncode = sEnc;
   }

  /**
   * AutoDetecting OS and run the correct shell command.
   */
  public int exec(String sCmd[]){
    String sOS = System.getProperty( "os.name" ).toUpperCase();
    //System.out.println(sOS);
    if( sOS.indexOf(WINDOWS)!=-1 || sOS.equalsIgnoreCase(WINDOWS_7) || sOS.equalsIgnoreCase(WINDOWS_2K) || sOS.equalsIgnoreCase(WINDOWS_NT) || sOS.equalsIgnoreCase(WINDOWS_XP)|| sOS.equalsIgnoreCase(WINDOWS_10) )
      return execInWinNT(sCmd);
    if( sOS.equalsIgnoreCase(WINDOWS_95) || sOS.equalsIgnoreCase(WINDOWS_98) )
      return execInWin9x(sCmd);
    if( sOS.equalsIgnoreCase(LINUX) || sOS.equalsIgnoreCase(FREEBSD) || sOS.equalsIgnoreCase(AIX) || sOS.equalsIgnoreCase(IRIX) || sOS.equalsIgnoreCase(SOLARIS) || sOS.equalsIgnoreCase(HP_UX) )
      return execInUNIX(sCmd);

    return -1;    
  }

  /**
   * run in win9x.
   */
  private int execInWin9x(String sCmd[]){
    try{
      Runtime aRT = Runtime.getRuntime(); // Runtime.getRuntime();
      /* Spawn a shell sub-process */
      Process aProc = aRT.exec( "command.com" );
      return exec(aProc,sCmd);
    }catch(IOException ex){ 
      ex.printStackTrace();
    }

    return -1;
  }

  /**
   * run in winNT,win2000.
   */
  private int execInWinNT(String sCmd[]){
    try{
      Runtime aRT = Runtime.getRuntime(); // Runtime.getRuntime();
      /* Spawn a shell sub-process */
      Process aProc = aRT.exec( "cmd.exe" );
      return exec(aProc,sCmd);
    }catch(IOException ex){
      ex.printStackTrace();
    }
    return -1;
  }

  /**
   *   run in UNIX.
   */
  private int execInUNIX(String sCmd[]){
    try{
      Runtime aRT = Runtime.getRuntime(); // Runtime.getRuntime();
      /* Spawn a shell sub-process */
      Process aProc = aRT.exec( "sh" );
      return exec(aProc,sCmd);
    }catch(IOException ex){
      ex.printStackTrace();
    }
    return -1;
  }

  /**
   * General Exe method.
   */
  private int exec(Process aProc,String sCmd[]){
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
      int exitVal = aProc.exitValue();
      System.out.println("exitval="+exitVal);

      aProc.getErrorStream().close();
      aProc.getInputStream().close();      
      sNormalMessage = outputGobbler.getInput();
      sErrorMessge = errorGobbler.getInput();
      outputGobbler.setStopFlag(true);
      errorGobbler.setStopFlag(true);
      return exitVal;
    }catch(Exception ioex){
      ioex.printStackTrace();
    }
    return -1;
  }
  
  public String getNormalMessage(){
	  return sNormalMessage;
  }
  
  public String getErrorMessage(){
	  return sErrorMessge;
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
     String[] aCmd=new String[3];
//     aCmd[0] = "mkdir c:\\123";
     aCmd[0] = "d:\\automa\\automa d:\\automa\\test.at";
     aCmd[1] = "echo hello";
     aCmd[2] = "exit";
     oShellCmd.exec(aCmd);
     System.out.println("normal message="+oShellCmd.getNormalMessage());
     System.out.println("error message="+oShellCmd.getErrorMessage() );
     System.out.println("test");
  }
}