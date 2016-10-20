package inmethod.commons.NumberGen;

/**
 * 若取得唯一值失敗,會出現NumException的訊息.
 */

public class NumException extends Exception {

  private String err=null;

  public NumException() {
    super();
  }

  /**
   * Num exception
   */
  public NumException(String str){
     this.err=str;
  }

  /**
   * get error string
   */
  public String getError(){
     return this.err;
  }

}