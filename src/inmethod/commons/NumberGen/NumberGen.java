package inmethod.commons.NumberGen;

/**
 * 取得唯一值,此唯一值由timesamp組成,外加一個seed number.
 */
public class NumberGen {

   protected static int i;

   /**
    * 取得唯一數值,數值格式 : [輸入的參數 + timesamp].
    * @param sID  唯一值的一部份
    * @return String 唯一值
    */
   public synchronized static String getNextNumber(String sID) {
     if( sID==null )
       return Long.toString(System.currentTimeMillis()) + getSeed();
     else
       return sID + Long.toString(System.currentTimeMillis()) + getSeed();
   };

   /**
    * private 取得內部的seed number
    * @return int seed number
    */
   private static int getSeed(){
     if( (i+1)>=10 ) i=0;
     else i++;
     return i;
   }

}