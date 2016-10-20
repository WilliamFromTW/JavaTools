package inmethod.commons.util;
import java.util.*;
import java.io.*;

/**
 * 取得目前系統所有屬性(設定).
 * @author wallersh
 */
public class DumpSysProp {
  public static void main(String[] args) {
    if(args.length<1){
      System.out.println("java DumpSysProp <dump_filename>");
      System.exit(-1);
    }
    else{
      File f = new File(args[0]);
      FileWriter fw = null;
      try{
        fw = new FileWriter(f);
        Properties aSysProp = System.getProperties();
        Enumeration aProps = aSysProp.propertyNames();
        fw.write("-------------- Java's system properties list --------------------\n");
        while(aProps.hasMoreElements()){
          String sPropName = (String)aProps.nextElement();
          String sPropValue = aSysProp.getProperty(sPropName);
          fw.write(sPropName + " = "+ sPropValue + "\n");
        }
        fw.flush();
        fw.close();
      }catch(IOException e){
        System.out.println("IO Error!");
        System.exit(-1);
      }
    }
  }
}