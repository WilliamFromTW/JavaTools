package inmethod.commons.util;

/**
 * 阿拉伯數字轉中文數字.
 * @author william chen
 */
public class ENum2CNum {

  public static final int ORAL_CHINESE_NUMBER1 = 1;
  public static final int ORAL_CHINESE_NUMBER2 = 2;
  public static final int ORAL_CHINESE_NUMBER3 = 4;
  public static final int FULL_TYPE_NUMBER = 8;
  public static final int CHINESE_TYPE_NUMBER1 = 16;
  public static final int CHINESE_TYPE_NUMBER2 = 32;

  /**
   * trans english number to chinese number .
   * <pre>
   *  阿拉伯數字依中文的種類不同轉成不同的結果
   * 參數 iChineseType==ENum2CNum.ORAL_CHINESE_NUMBER1 :  3001105->三百萬一千一百零五
   * 參數 iChineseType==ENum2CNum.ORAL_CHINESE_NUMBER2 :  3001105->參佰萬壹仟壹佰零伍
   * 參數 iChineseType==ENum2CNum.ORAL_CHINESE_NUMBER3 :  1705 -> 壹仟柒佰零拾伍
   * 參數 iChineseType==ENum2CNum.FULL_TYPE_NUMBER :  123123->一二三一二三
   * 參數 iChineseType==ENum2CNum.CHINESE_TYPE_NUMBER1 :  123123->１２３１２３
   * 參數 iChineseType==ENum2CNum.CHINESE_TYPE_NUMBER2 :  123123->壹貳參壹貳參
   * </pre>
   * @param iValue 欲轉換的阿拉伯數值
   * @param iChineseType 轉換的中文種類
   * @return 轉換後的中文數值
   */
  public static String convertToChineseNumber(int iValue,int iChineseType){
    if( iChineseType == ORAL_CHINESE_NUMBER1 )
      return toOralChinese(iValue);
    if( iChineseType == ORAL_CHINESE_NUMBER2 )
      return toOralMoneyChinese(iValue);
    if( iChineseType == ORAL_CHINESE_NUMBER3 )
      return toCheckChinese(iValue);
    if( iChineseType == FULL_TYPE_NUMBER )
      return toFullType(iValue);
    if( iChineseType == CHINESE_TYPE_NUMBER1 )
      return toChinese(iValue);
    if( iChineseType == CHINESE_TYPE_NUMBER2 )
      return toMoneyChinese(iValue);
    return null;
  }

  /**
   * 數字轉成全形數字.
   * @param iValue 阿拉伯數字
   * @return String 全形阿拉伯數字
   */
  private static String toFullType(int iValue){
    int iLength = String.valueOf(iValue).length();
    String strValue = String.valueOf(iValue);
    StringBuffer sb = new StringBuffer("");
    int i=0;
    for(i=0;i<iLength-1;i++){
      sb.append( replaceToFullType(Integer.parseInt(strValue.substring(i,i+1))) );
    }
    sb.append( replaceToFullType(Integer.parseInt(strValue.substring(i))) );
    return sb.toString();
  }

  /**
   * 數字轉成中文數字.
   * @param iValue 阿拉伯數字
   * @return String 中文數字
   */
  private static String toChinese(int iValue){
    int iLength = String.valueOf(iValue).length();
    String strValue = String.valueOf(iValue);
    StringBuffer sb = new StringBuffer("");
    int i=0;
    for(i=0;i<iLength-1;i++){
      sb.append( replaceToChinese(Integer.parseInt(strValue.substring(i,i+1))) );
    }
    sb.append( replaceToChinese(Integer.parseInt(strValue.substring(i))) );
    return sb.toString();
  }

  /**
   * 數字轉成中文數字.
   * @param iValue 阿拉伯數字
   * @return String 中文數字
   */
  private static String toMoneyChinese(int iValue){
    int iLength = String.valueOf(iValue).length();
    String strValue = String.valueOf(iValue);
    StringBuffer sb = new StringBuffer("");
    int i=0;
    for(i=0;i<iLength-1;i++){
      sb.append( replaceToMoneyChinese(Integer.parseInt(strValue.substring(i,i+1))) );
    }
    sb.append( replaceToMoneyChinese(Integer.parseInt(strValue.substring(i))) );
    return sb.toString();
  }

  /**
   * 轉成支票型態的金錢書寫方法,如 100005 => 壹拾萬零萬零仟零佰零拾伍.
   */
  private static String toCheckChinese(int iNumber){
    return toCheckChinese(iNumber,Integer.toString(iNumber).length());
  }

  /**
   * 轉成支票型態的金錢書寫方法,如 100005 => 壹拾萬零萬零仟零佰零拾伍.
   * 如
   *   toCheckChinese(100005,7," ") => 零百壹拾零萬零仟零佰零拾伍
   * @param iNumber 欲轉換的數字
   * @param iFormatLength  轉換後的數字格式
   */
  private static String toCheckChinese(int iNumber,int iFormatLength){
    return toCheckChinese(iNumber,iFormatLength,null);
  }
  /**
   * 轉成支票型態的金錢書寫方法.
   * 如
   *   toCheckChinese(100005,7," ") => 零 百 壹 拾 零 萬 零 仟 零 佰 零 拾 伍
   * @param iNumber int 欲轉換的數字
   * @param iFormatLength int 轉換後的數字格式
   * @param strSpace String 區隔
   */
  private static String toCheckChinese(int iNumber,int iFormatLength,String strSpace){
    int iLength = Integer.toString(iNumber).length();
    if( strSpace==null ) strSpace="";
    String sValue = Integer.toString(iNumber);
    String sReturn = "";
    int j=0;
    if( iFormatLength>iLength )
      j = iFormatLength;
    else
      j = iLength;
    for(int i=0;i<j;i++){
      int iValue = 0;
      try{
        iValue = Integer.parseInt(sValue.substring( iLength-i-1,iLength-i ));
      }catch(Exception ex){};
      switch(i){
        case 0: sReturn = strSpace + replaceToMoneyChinese(iValue) ; break;
        case 1: sReturn = strSpace + replaceToMoneyChinese(iValue) + strSpace + "拾" + sReturn; break;
        case 2: sReturn = strSpace + replaceToMoneyChinese(iValue) + strSpace + "佰" + sReturn; break;
        case 3: sReturn = strSpace + replaceToMoneyChinese(iValue) + strSpace + "仟" + sReturn; break;
        case 4: sReturn = strSpace + replaceToMoneyChinese(iValue) + strSpace + "萬" + sReturn; break;
        case 5: sReturn = strSpace + replaceToMoneyChinese(iValue) + strSpace + "拾" + sReturn; break;
        case 6: sReturn = strSpace + replaceToMoneyChinese(iValue) + strSpace + "佰" + sReturn; break;
        case 7: sReturn = strSpace + replaceToMoneyChinese(iValue) + strSpace + "仟" + sReturn; break;
        case 8: sReturn = strSpace + replaceToMoneyChinese(iValue) + strSpace + "億" + sReturn; break;
        case 9: sReturn = strSpace + replaceToMoneyChinese(iValue) + strSpace + "拾" + sReturn; break;
        case 10: sReturn = strSpace + replaceToMoneyChinese(iValue) + strSpace + "佰" + sReturn; break;
        case 11: sReturn = strSpace + replaceToMoneyChinese(iValue) + strSpace + "仟" + sReturn; break;

      }
    }
    return sReturn;
  }
  /**
   * 數字轉成口語的中文數字,只能到億.
   * @param iNumber 阿拉伯數字
   * @return String 中文數字
   */
  private static String toOralChinese(int iNumber){
    int iLength = Integer.toString(iNumber).length();
    String sValue = Integer.toString(iNumber);
    StringBuffer sReturn = new StringBuffer("");
    int j=0,k=0;
    int iContinueZero = 0;
    for(int i=0;i<iLength;i++){

      int iValue = Integer.parseInt(sValue.substring( iLength-i-1,iLength-i ));
      if(iValue==0)
        iContinueZero++;
      else
        iContinueZero=0;
      if( i== 1 && iValue!=0 )  sReturn.insert(0,"十");
      if( i== 2 && iValue!=0  )  sReturn.insert(0,"百");
      if( i== 3 && iValue!=0  )  sReturn.insert(0,"千");
      if( i== 4 )  sReturn.insert(0,"萬");
      if( i== 5 && iValue!=0   )  sReturn.insert(0,"十");
      if( i== 6 && iValue!=0   )  sReturn.insert(0,"百");
      if( i== 7 && iValue!=0   )  sReturn.insert(0,"千");
      if( i== 8 && iValue!=0   )  sReturn.insert(0,"億");
      if( i== 9 && iValue!=0   )  sReturn.insert(0,"十");
      if( (i==1 || i==5 || i==9) && i==iLength-1 && sValue.substring( iLength-i-1,iLength-i ).equals("1"))
      ;
      else{

        if( (i-1)<=iLength && (0==Integer.parseInt(sValue.substring( iLength-i-1 )) ))
          ;
        else{
          if( (i-1)<=iLength && i>=4 && (0==Integer.parseInt(sValue.substring( iLength-i-1,iLength-4))))
            ;
          else{

            String sTemp = replaceToChinese(iValue);
            if( iContinueZero<=1)
            sReturn.insert(0, sTemp);
          }
        }
      }
    }
    return sReturn.toString();
  }


  /**
   * 數字轉成口語的中文數字(金錢),只能到億.
   * @param iNumber 阿拉伯數字
   * @return String 中文數字
   */
  private static String toOralMoneyChinese(int iNumber){
    int iLength = Integer.toString(iNumber).length();
    String sValue = Integer.toString(iNumber);
    StringBuffer sReturn = new StringBuffer("");
    int j=0,k=0;
    int iContinueZero = 0;
    for(int i=0;i<iLength;i++){

      int iValue = Integer.parseInt(sValue.substring( iLength-i-1,iLength-i ));
      if(iValue==0)
        iContinueZero++;
      else
        iContinueZero=0;
      if( i== 1 && iValue!=0 )  sReturn.insert(0,"拾");
      if( i== 2 && iValue!=0  )  sReturn.insert(0,"佰");
      if( i== 3 && iValue!=0  )  sReturn.insert(0,"仟");
      if( i== 4 )  sReturn.insert(0,"萬");
      if( i== 5 && iValue!=0   )  sReturn.insert(0,"拾");
      if( i== 6 && iValue!=0   )  sReturn.insert(0,"佰");
      if( i== 7 && iValue!=0   )  sReturn.insert(0,"仟");
      if( i== 8 && iValue!=0   )  sReturn.insert(0,"億");
      if( i== 9 && iValue!=0   )  sReturn.insert(0,"拾");
      if( (i==1 || i==5 || i==9) && i==iLength-1 && sValue.substring( iLength-i-1,iLength-i ).equals("1"))
        sReturn.insert(0,replaceToMoneyChinese(iValue));
      else{

        if( (i-1)<=iLength && (0==Integer.parseInt(sValue.substring( iLength-i-1 )) ))
          ;
        else{
          if( (i-1)<=iLength && i>=4 && (0==Integer.parseInt(sValue.substring( iLength-i-1,iLength-4))))
            ;
          else{

            String sTemp = replaceToMoneyChinese(iValue);
            if( iContinueZero<=1)
              sReturn.insert(0, sTemp);
          }
        }
      }
    }
    return sReturn.toString();
  }

  /**
   * 個位數字轉成全形.
   * @param i must be 0 ~ 9
   * @return 全形數字
   */
  private static String replaceToFullType(int i){
    switch(i){
      case 0: return "０";
      case 1: return "１";
      case 2: return "２";
      case 3: return "３";
      case 4: return "４";
      case 5: return "５";
      case 6: return "６";
      case 7: return "７";
      case 8: return "８";
      case 9: return "９";
      default: return null;
    }
  }

  /**
   * 個位數字轉成中文數字.
   * @param i must be 0 ~ 9
   * @return 中文數字
   */
   private static String replaceToChinese(int i){
     switch(i){
       case 0: return "零";
       case 1: return "一";
       case 2: return "二";
       case 3: return "三";
       case 4: return "四";
       case 5: return "五";
       case 6: return "六";
       case 7: return "七";
       case 8: return "八";
       case 9: return "九";
       default:
         return null;
     }
   }

  /**
   * 個位數字轉成中文數字(金錢).
   * @param i must be 0 ~ 9
   * @return 中文數字
   */
   private static String replaceToMoneyChinese(int i){
     switch(i){
       case 0: return "零";
       case 1: return "壹";
       case 2: return "貳";
       case 3: return "參";
       case 4: return "肆";
       case 5: return "伍";
       case 6: return "陸";
       case 7: return "柒";
       case 8: return "捌";
       case 9: return "玖";
       default:
         return null;
     }
   }

  /**
   * main test.
   * <pre>
   * 測試method
   *  toOralChinese(3001105) -> 三百萬一千一百零五
   *  toChinese(123123) -> 一二三一二三
   *  toFullType(123123) -> １２３１２３
   * </pre>
   */
   public static void main(String[] a){
     // 數字轉成口語的中文數字; 3001105->三百萬一千一百零五
     System.out.println( ENum2CNum.convertToChineseNumber(3001105,ORAL_CHINESE_NUMBER1));
     // 數字轉成支票中文數字; 3001105->參佰萬壹仟壹佰零伍
     System.out.println( ENum2CNum.convertToChineseNumber(3001105,ORAL_CHINESE_NUMBER2));
     // 轉成支票中文數字 1705 -> 壹仟柒佰零拾伍
     System.out.println( ENum2CNum.convertToChineseNumber(1705,ORAL_CHINESE_NUMBER3));
     // 數字轉成中文數字; 123123->一二三一二三
     System.out.println( ENum2CNum.convertToChineseNumber(123123,CHINESE_TYPE_NUMBER1));
     // 數字轉成中文數字; 123123->壹貳參壹貳參
     System.out.println( ENum2CNum.convertToChineseNumber(123123,CHINESE_TYPE_NUMBER2));
     // 數字轉成全形數字; 123123->１２３１２３
     System.out.println( ENum2CNum.convertToChineseNumber(123123,FULL_TYPE_NUMBER));
   }
}