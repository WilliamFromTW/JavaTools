package inmethod.commons.util;


	/**
	 * 若取得唯一值失敗,會出現NumException的訊息.
	 */

	public class NotByteException extends Exception {

		  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		

		   private String sError = "";
		   
		   public NotByteException() {
		   }

		   public NotByteException(String message) {
		       super(message);
		       sError = message;
		   }

		   public NotByteException(Throwable cause) {
		       super(cause);
		   }

		   public NotByteException(String message, Throwable cause) {
		       super(message, cause);
		       sError = message;
		   }
		   public void printStacktrace() {
			   System.err.println(sError);
			   super.printStackTrace();
		   }
		}
