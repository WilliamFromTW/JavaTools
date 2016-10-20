// TestExeShellCmd.java
package test;
import inmethod.commons.util.ExeShellCmd;

/**
 * 測試 ExeShellCmd.
 * @see ExeShellCmd
 */
public class TestExeShellCmd{
  public static void main(String[] args) {

    ExeShellCmd oShellCmd = new ExeShellCmd();
    // dir
    String[] aCmd=new String[2];
    aCmd[0] = "dir c:\\temp";
    aCmd[1] = "dir d:\\temp";
    oShellCmd.exec(aCmd);
  }
}