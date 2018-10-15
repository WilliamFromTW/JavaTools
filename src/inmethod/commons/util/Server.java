package inmethod.commons.util;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 * 一個TCP/IP Client Server 的Framework,可以用來開發自己的c/s系統.
 * @author william chen
 * @see inmethod.commons.util.ServerProcessor
 */
public class Server{
  /**
   * entry point of this class.
   */
  public void start(int iPort,String sProcessName) {

    ServerSocket ss;
    // set the port to listen on

    try {
      ss = new ServerSocket(iPort);
      while (true) {
        ServerThread server = new ServerThread(ss.accept(),sProcessName);    // fork a thread , when a client attach
        server.start();
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }

  }

}

class ServerThread extends Thread {

  private Socket theConnection;
  private ServerProcessor aServerProcessor;
  private String sServerProcessName;

  public ServerThread(Socket s,String sServerProcessName) {
    theConnection = s;
    this.sServerProcessName = sServerProcessName;
    try{
      aServerProcessor =(ServerProcessor) ((ServerProcessor)Reflection.newInstance(sServerProcessName,null)).clone();
      aServerProcessor.addConnection(theConnection);
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }
 

  /**
   * when method "start" called , this method will called next if class extends thread.
   */
  public void run() {
    try{
    /*
      aServerProcessor = (ServerProcessor)Reflection.newInstance(sServerProcessName,null);
      aServerProcessor.addConnection(theConnection);
      Log.debug("Server","ServerProcessor ready to run");
      */
      aServerProcessor.run();
      theConnection.close();
      aServerProcessor = null;
      Runtime rt = java.lang.Runtime.getRuntime();
      System.out.println("Total heap: " + rt.totalMemory());
      System.out.println("Total free: " + rt.freeMemory() );
      System.out.println("do gc: ");
      rt.gc();

      Thread t = Thread.currentThread();
      t = null;
    }catch(Exception ex){
      ex.printStackTrace();
    }

  }

}