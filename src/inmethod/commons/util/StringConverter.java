package inmethod.commons.util;
import java.io.*;
import java.util.*;
import java.text.*;

/**
 * 將字串以不同內碼重新編譯.
 * @author william chen
 */
public class StringConverter {

  private static final String sEncode = "BIG5";

  public StringConverter() {
  }

  /**
   * Do strng encode
   * @param s
   * @return String default encode with big5
   * @throws Exception
   */
  public static String encode(String s) throws Exception{
    return encode(s,sEncode);
  }

  /**
   * Do strng encode
   * @param s
   * @param sEncode encode
   * @return String after encode
   * @throws Exception
   */
  public static String encode(String s,String sEncode) throws Exception{
    try{
      return new String(s.getBytes(),sEncode );
    }catch(Exception ex){
      throw new Exception("String encode fail");
    }
  }

  /**
   * convert Date to String
   * @param dateArg
   * @return String with format "yyyy-MM-dd HH:mm:ss"
   * @throws Exception
   */
  public static String DateToStr(java.util.Date dateArg) throws Exception{
    try{
      SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return Dateformat.format(dateArg);
    }catch(Exception e){
      throw new Exception("<<Converter>>DateToStr :: The value of dateArg is illegal");
    }
  }

  /**
   * find String begin with sBegin,end with End
   * @param sSource source string
   * @param sBegin begin string
   * @param sEnd end String
   * @return Vector each matched string
   */
  public static Vector find(String sSource,String sBegin,String sEnd) throws Exception{
    return find(sSource,sBegin,sEnd,true);
  }

  /**
   * find String begin with sBegin,end with End
   * @param sSource source string
   * @param sBegin begin string
   * @param sEnd end String
   * @param bCaseSense true: caseSense ; false: caseInSense
   * @return Vector each matched string
   */
  public static Vector find(String sSource,String sBegin,String sEnd,boolean bCaseSense) throws Exception{
    Vector aVector = new Vector();
    String sSourceClone = null;
    String sBeginClone = null;
    String sEndClone = null;
    if( !bCaseSense ){
      sSourceClone = new String(sSource.toUpperCase());
      sBeginClone = new String(sBegin.toUpperCase());
      sEndClone = new String(sEnd.toUpperCase());
    }
    else{
      sSourceClone = new String(sSource);
      sBeginClone = new String(sBegin);
      sEndClone = new String(sEnd);
    }

    int iStart = 0;
    int iEnd = 0;
    do{
      iStart = sSourceClone.indexOf(sBeginClone,iStart);
      iEnd = sSourceClone.indexOf(sEndClone,iStart);
      if( iStart==-1 || iEnd==-1 ) break;
      aVector.add( sSource.substring(iStart,iEnd+1) );
      iStart = iEnd-1;
      iEnd = iEnd-1;
    }while(1==1);
    return aVector;
  }

  /**
   * insert string after specify word
   * @param sSource source string
   * @param sKeyWord the word will insert behind
   * @param sInsertString string want to insert
   */
  public static String insert(String sSource,String sKeyWord,String sInsertString) throws Exception{
    return insert(sSource,sKeyWord,sInsertString);
  }

  /**
   * insert string after specify word
   * @param sSource source string
   * @param sKeyWord the word will insert behind
   * @param sInsertString string want to insert
   * @param bCaseSense true: caseSense ; false: caseInSense
   */
  public static String insert(String sSource,String sKeyWord,String sInsertString,boolean bCaseSense) throws Exception{
    StringBuffer aSB = new StringBuffer(sSource);
    String sTemp = null;
    String sKeyWordClone = null;
    if( !bCaseSense ){
      sKeyWordClone = new String(sKeyWord.toUpperCase());
    }
    else{
      sKeyWordClone = new String(sKeyWord);
    }
    int iStart = 0;
    int iLength = sKeyWord.length();
    do{
      if( !bCaseSense ){
        sTemp = aSB.toString().toUpperCase();
      }
      else{
        sTemp = aSB.toString();
      }

      iStart = sTemp.indexOf(sKeyWordClone,iStart);
      if( iStart==-1 ) break;
      iStart += iLength;
      aSB = aSB.insert(iStart,sInsertString);
    }while(1==1);
    return aSB.toString();
  }

  /**
   * replace string after specify word
   * @param sSource source string
   * @param sKeyWord the word will replace behind
   * @param sReplaceString string want to replace
   */
  public static String replace(String sSource,String sKeyWord,String sReplaceString) throws Exception{
    return replace(sSource,sKeyWord,sReplaceString,true);
  }

  /**
   * replace string after specify word
   * @param sSource source string
   * @param sKeyWord the word will replace behind
   * @param sReplaceString string want to replace
   * @param bCaseSense true: caseSense ; false: caseInSense
   */
  public static String replace(String sSource,String sKeyWord,String sReplaceString,boolean bCaseSense) throws Exception{
    StringBuffer aSB = new StringBuffer(sSource);
    String sSourceClone = null;
    String sKeyWordClone = null;
    if( !bCaseSense ){
      sSourceClone = new String(sSource.toUpperCase());
      sKeyWordClone = new String(sKeyWord.toUpperCase());
    }
    else{
      sSourceClone = new String(sSource);
      sKeyWordClone = new String(sKeyWord);
    }
    int iStart = 0;
    int iEnd = 0;
    int iOffset = 0;
    String sTemp = null;
    do{
      iStart = sSourceClone.indexOf(sKeyWordClone,iStart);
      iEnd = iStart+sKeyWord.length();
      if( iStart==-1 || iEnd==-1 ) break;
      aSB.replace(iStart-iOffset,iEnd-iOffset,sReplaceString);
      iStart = iEnd;
      iOffset += sKeyWord.length()-sReplaceString.length();
    }while(1==1);
    return aSB.toString();
  }

  /**
   * 將字串上有 ' 符號加上 跳脫符號.
   * 若有exception則回傳原字串
   * @param sInput source string
   * @return String
   */
  public static String addBackSlashes(String sInput){
    if( sInput == null ) return null;
    try{
     return  replace( replace(sInput,"\"","\\\""),"'","\\'");
    }catch(Exception ex){
      ex.printStackTrace();
      return sInput;
    }
  }

  /**
   * 將字串上有跳脫符號拿掉
   * 若有exception則回傳原字串
   * @param sInput source string
   * @return String
   */
  public static String stripBackSlashes (String sInput){
    if( sInput == null ) return null;
    try{
     return  replace( replace(sInput,"\\\"","\""),"\\'","'");
    }catch(Exception ex){
    	 ex.printStackTrace();
      return sInput;
    }
  }
  /**
   * sprintf like c.
   * <pre>
   *   ex1:
   *       aVector.add("william");
   *       sprintf("this is prince %10s", aVector) =>   "this is prince william   " // default is left-align
   *       sprintf("this is prince %-10s", aVector) =>  "this is prince william   " // left align
   *       sprintf("this is prince %+10s", aVector) =>  "this is prince    william" // right align
   *       sprintf("this is prince %4s",aVector) =>     "this is prince will" // truncate
   *       aVector.add("lois");
   *       sprintf("this is prince %s and princess %s",aVector) => "this is prince william and princess lois"
   *   ex2:
   *       aVector.add(new Integer(10000);
   *       sprintf("this is %10d ntd",aVector) =>    "this is      10000 ntd" // default is right align
   *       sprintf("this is %+10d ntd",aVector) =>   "this is      10000 ntd" // default is left align
   *       sprintf("this is %-10d ntd",aVector) =>   "this is 10000      ntd" // left align
   *       sprintf("this is %10.3d ntd",aVector) =>  "this is   10000    ntd" // left align , but 3 spaces near "left" is reserved for float point
   *       sprintf("this is %d ntd",aVector)   =>   "this is 1000 ntd" // truncate
   *       sprintf("this is %'-10d ntd",aVector)=>   "this is 10,000     ntd" // thousand separate
   *       sprintf("this is %####000000d ntd",aVector)=> "this is     010000 ntd" // #: if no value will not show out, 0: show out any way
   *       sprintf("this is %####00,000d ntd",aVector)=>"this is      10,000 ntd" // #: if no value will not show out, 0: show out any way
   *       aVector.add(new Integer(200);
   *       sprintf("this is %d and %d ", aVector) => "this is 10000 and 200";
   *   d could be replaced by f(Float) and d(Double)
   * </pre>
   * @param strFormat a String is composed of string and some signs that will be convert according the next Parameter.
   * @param aParamVector if param 'strFormat' have some sings , aParamVector must match the sings describe in param 'strFormat'
   * @return string after be translated.
   * @throws Exception <pre>
   *              if format of param "strFormat" is error or
   *              if param "strFormat" is null  or
   *              if data type of param 'aParamVector' error or
   *              if param "aParamVector" is null or
   *              if the # of signs in param "strFormat" don't match the # of param "aParamVector"
   *              </pre>
   */
  public static String sprintf(String strFormat,Vector aParamVector) throws Exception{
    HashMap aHM = parseSprintfFormat(strFormat);
    String sReturn = "";
    Vector aDataVector = (Vector)aHM.get("Data");
    Vector aTypeVector = (Vector)aHM.get("Type");
    if( ((Integer)aHM.get("SignCount")).intValue() != aParamVector.size() ) throw new Exception("sign # arent' mathch vector value");
    int j=-1;
    for(int i=0; i<aTypeVector.size();i++){

      if( aTypeVector.get(i).equals("1") ){
        j++;
        sReturn += singleSprintf( (String) aDataVector.get(i),aParamVector.get(j));
      }
      else
        sReturn += aDataVector.get(i);
    }
    return sReturn;
  }

  /**
   * used for public method 'sprintf'
   * @param strFormat string formater
   * @return HashMap
   * <pre>
   *           key = "Data" , value return is Vector object include data information
   *           key = "Type" , value return is Vector object include sign information
   *           key = "SignCount" , value return is Integer of total sign # , 0 = no signs
   * </pre>
   * @throws Exception
   * <pre>
   *           if strFormat is null or
   *           the format of strFormat is error
   * </pre>
   */
  private static HashMap parseSprintfFormat(String strFormat) throws Exception{
    if( strFormat == null ) throw new Exception("param strFormat is null");
    Vector aDataVector = new Vector();
    Vector aTypeVector = new Vector();
    HashMap aReturnHM = new HashMap();
    aReturnHM.put("Data",aDataVector);
    aReturnHM.put("Type",aTypeVector);
    int iIndex = 0;
    int iTempBeginIndex = 0;
    int iTempEndIndex = -1;
    int iLength = strFormat.length();
    boolean bGotPercentSign = false;
    boolean bGotDataTypeSign = false;
    String strTempFormat = new String(strFormat);
    int iSignCount = 0;
    while( true ){
      bGotPercentSign = false;
      bGotDataTypeSign = false;
      iTempBeginIndex = 0;
      iTempEndIndex = -1;
      int iSIndex = 0;
      int iDIndex = 0;
      int iFIndex = 0;
      int iDoubleIndex = 0;
      if( strTempFormat.indexOf("%")!=-1 ){
        bGotPercentSign = true;
        iTempBeginIndex = strTempFormat.indexOf("%");

        if( strTempFormat.indexOf("s",iTempBeginIndex)!=-1 ){
          iSIndex = strTempFormat.indexOf("s",iTempBeginIndex);
          iTempEndIndex = iSIndex;
        }else if( strTempFormat.indexOf("d",iTempBeginIndex)!=-1 ){
          iDIndex = strTempFormat.indexOf("d",iTempBeginIndex);
          if( iTempEndIndex == -1 ) iTempEndIndex = iDIndex;
          else
            if( iTempEndIndex>iDIndex) iTempEndIndex = iDIndex;
        }else if( strTempFormat.indexOf("f",iTempBeginIndex)!=-1 ){
          iFIndex = strTempFormat.indexOf("f",iTempBeginIndex);
          if( iTempEndIndex == -1 ) iTempEndIndex = iFIndex;
          else
            if( iTempEndIndex>iFIndex) iTempEndIndex = iFIndex;
        }else if( strTempFormat.indexOf("d",iTempBeginIndex)!=-1 ){
          iDoubleIndex = strTempFormat.indexOf("d",iTempBeginIndex);
          if( iTempEndIndex == -1 ) iTempEndIndex = iDoubleIndex;
          else
            if( iTempEndIndex>iFIndex) iTempEndIndex = iDoubleIndex;
        }
        if( iTempEndIndex!=-1 )
          bGotDataTypeSign = true;

      }
      if( bGotPercentSign && bGotDataTypeSign ){
        if( iTempBeginIndex>=0 ){
          aDataVector.add(new String(strTempFormat.substring(0,iTempBeginIndex)));
          aTypeVector.add("0"); // 0 is nothing
          aDataVector.add(new String(strTempFormat.substring(iTempBeginIndex,iTempEndIndex+1)));
          aTypeVector.add("1"); // 0 is nothing
        }
        else{
          aDataVector.add(new String(strTempFormat.substring(0,iTempEndIndex)));
          aTypeVector.add("1"); // 0 is nothing
        }
        iSignCount ++;
        strTempFormat = strTempFormat.substring(iTempEndIndex+1);
        if( strTempFormat.length()==0 ) break;
      }
      else {
        aDataVector.add( new String(strTempFormat)  );
        aTypeVector.add("0");
        break;
      }
    }

    aReturnHM.put("SignCount",new Integer(iSignCount));
    return aReturnHM;
  }

  /**
   * string after be translated.
   * used for method "sprintf"
   * @param strSign only sign ex: %2d
   * @param objData Data object
   * @return string after be translated.
   * @throws Exception
   * <pre>
   *   if format of strSign is error or null or
   *   if objData is null or type error
   * </pre>
   */
  private static String singleSprintf(String strSign,Object objData) throws Exception{
    if( strSign==null || objData == null ) throw new Exception("param null");
    int iTempIndex = 0;
    int iLength = 0;
    int iDotLength = 0;
    int iDataTypeIndex = 0;
    boolean bAlignLeft = false;
    boolean bThousandSeparate = false;
    String strTempValue = null;
    String strReturn = "";
    if( strSign.startsWith("%") ){
      /** process the sign "s" */
      if( (iDataTypeIndex =  strSign.indexOf ("s"))!=-1 ){
        if( !(objData instanceof String ) ) throw new Exception("data type must be String ");
        if( (iTempIndex = strSign.indexOf("-"))!=-1 ){
          strReturn = getStringAfterAlign(true, Integer.parseInt(strSign.substring(iTempIndex+1,iDataTypeIndex)),(String)objData);
        }
        if( (iTempIndex = strSign.indexOf("+"))!=-1 ){
          try{
            strReturn = getStringAfterAlign(false, Integer.parseInt(strSign.substring(iTempIndex+1,iDataTypeIndex)),(String)objData);
          }catch(Exception ex){ return (String)objData; };
        }
        else{
          try{
            strReturn = getStringAfterAlign(true, Integer.parseInt(strSign.substring(1,iDataTypeIndex)),(String)objData);
          }catch(Exception ex){ return (String)objData; };
        }
      }

      /** process the sign "d" */
      if( (iDataTypeIndex =  strSign.indexOf ("d"))!=-1 ){
        Locale currentLocale = null;
        String strDecimalFormat = null;
        if( (iTempIndex = strSign.indexOf("'"))!=-1 ){ // thousand separate
          currentLocale = new Locale("en", "US");
          bThousandSeparate = true;
          strSign = strSign.substring(1);
          iDataTypeIndex--;
        }
        if( (iTempIndex = strSign.indexOf("+"))!=-1 ){ // thousand separate
          bAlignLeft = true;
          try{
            iLength = Integer.parseInt(strSign.substring(iTempIndex+1,iDataTypeIndex));
            strDecimalFormat = getDecimalFormat(bThousandSeparate,iLength,0);
            if( iLength == 0 ) throw new Exception();
          }catch(Exception ex){
            strDecimalFormat = strSign.substring(iTempIndex+1,iDataTypeIndex);
            iLength = strDecimalFormat.length();
          }
        }
        else{
          if( (iTempIndex = strSign.indexOf("-"))!=-1 ){ // thousand separate
            bAlignLeft = false;
            try{
              iLength = Integer.parseInt(strSign.substring(iTempIndex+1,iDataTypeIndex));
              strDecimalFormat = getDecimalFormat(bThousandSeparate,iLength,0);
              if( iLength == 0 ) throw new Exception();
            }catch(Exception ex){
              strDecimalFormat = strSign.substring(iTempIndex+1,iDataTypeIndex);
              iLength = strDecimalFormat.length();
            }
          }
          else{
            try{
              iLength = 0;
              iLength = Integer.parseInt(strSign.substring(1,iDataTypeIndex));
              strDecimalFormat = getDecimalFormat(bThousandSeparate,iLength,0);
              if( iLength == 0 ) throw new Exception();
            }catch(Exception ex){
              strDecimalFormat = strSign.substring(1,iDataTypeIndex);
              iLength = strDecimalFormat.length();
            }
          }
        }
        if( iLength==0 && !bThousandSeparate ) return ((Integer)objData).toString();

        NumberFormat nf = null;
        if( currentLocale!=null ) nf = NumberFormat.getNumberInstance(currentLocale);
        else nf =  NumberFormat.getNumberInstance();
        DecimalFormat df = (DecimalFormat)nf;
        df.applyPattern(strDecimalFormat);
        strReturn = df.format(objData);
        if( iLength>0 )
          strReturn = getStringAfterAlign(bAlignLeft,iLength,strReturn);

      }

      /** process the sign "f" */
      if( (iDataTypeIndex =  strSign.indexOf ("f"))!=-1 ){
        Locale currentLocale = null;
        String strDecimalFormat = null;
        if( (iTempIndex = strSign.indexOf("'"))!=-1 ){ // thousand separate
          currentLocale = new Locale("en", "US");
          bThousandSeparate = true;
          strSign = strSign.substring(1);
          iDataTypeIndex--;
        }
        if( (iTempIndex = strSign.indexOf("+"))!=-1 ){ // align left
          bAlignLeft = true;
          double dValue = 0d;
          int i;
          try{
            dValue = Double.parseDouble(strSign.substring(iTempIndex+1,iDataTypeIndex));
            iLength = (int)Math.floor(dValue);
            if( (i = strSign.indexOf("."))!=-1 ){ // get the dot length
              iDotLength = Integer.parseInt(strSign.substring(i+1,iDataTypeIndex));
            }
            strDecimalFormat = getDecimalFormat(bThousandSeparate,iLength,iDotLength);
            if( iLength == 0 && iDotLength == 0) throw new Exception();
          }catch(Exception ex){
            strDecimalFormat = strSign.substring(iTempIndex+1,iDataTypeIndex);
            iLength = strDecimalFormat.length();
          }
        }
        else{
          if( (iTempIndex = strSign.indexOf("-"))!=-1 ){ // align right
            bAlignLeft = false;
            double dValue = 0d;
            int i;
            try{
              dValue = Double.parseDouble(strSign.substring(iTempIndex+1,iDataTypeIndex));
              iLength = (int)Math.floor(dValue);
              if( (i = strSign.indexOf("."))!=-1 ){ // get the dot length
                iDotLength = Integer.parseInt(strSign.substring(i+1,iDataTypeIndex));
              }
              strDecimalFormat = getDecimalFormat(bThousandSeparate,iLength,iDotLength);
              if( iLength == 0 && iDotLength == 0) throw new Exception();
            }catch(Exception ex){
              strDecimalFormat = strSign.substring(iTempIndex+1,iDataTypeIndex);
              iLength = strDecimalFormat.length();
            }
          }
          else{
            try{
              iLength = 0;
              int i=0;
              double dValue = Double.parseDouble (strSign.substring(1,iDataTypeIndex));
              iLength = (int)Math.floor(dValue);
              if( (i = strSign.indexOf("."))!=-1 ){ // get the dot length
                iDotLength = Integer.parseInt(strSign.substring(i+1,iDataTypeIndex));
              }
              strDecimalFormat = getDecimalFormat(bThousandSeparate,iLength,iDotLength);
              if( iLength == 0 && iDotLength == 0) throw new Exception();
            }catch(Exception ex){
              strDecimalFormat = strSign.substring(1,iDataTypeIndex);
              iLength = strDecimalFormat.length();
            }
          }
        }
        if( iLength==0 && !bThousandSeparate ) return ((Integer)objData).toString();

        NumberFormat nf = null;
        if( currentLocale!=null ) nf = NumberFormat.getNumberInstance(currentLocale);
        else nf =  NumberFormat.getNumberInstance();
        DecimalFormat df = (DecimalFormat)nf;
        df.applyPattern(strDecimalFormat);
        strReturn = df.format(objData);
        if( iLength>0 )
          strReturn = getStringAfterAlign(bAlignLeft,iLength,strReturn);

      }


      return strReturn;
    }
    else
      throw new Exception("format error");
  }

  /**
   * String after align
   * used for private method "singleSPrinf"
   * @param bAlignLeft false  align right, true align left
   * @param iLength total length
   * @param strString Data
   * @return String after align
   */
  private static String getStringAfterAlign(boolean bAlignLeft,int iLength,String strString){
      int iStringLength = strString.getBytes().length;
      if( iStringLength >= iLength ){
        if( strString.length()<strString.getBytes().length ) {
          String sTemp = "";
          String sTemp2 = "";
          int j=0;
          for(int i=0;i<iLength;i++){
            sTemp2 = strString.substring(i,i+1);
            j += sTemp2.getBytes().length;
            if( j>iLength ){
             if( sTemp.getBytes().length<iLength)
               return sTemp+" ";
             else
               return sTemp;
           }
            else sTemp += sTemp2;
          }
          return sTemp;
        }
        else
          return strString.substring(0,iLength);
      }
      else{
        String sReturn = strString;
        for(int i=0;i<(iLength-iStringLength);i++){
          if( bAlignLeft ) sReturn += " ";
          else
            sReturn = " " + sReturn;
        }
        return sReturn;
      }
  }

  /**
   * get Decimal Format pattern.
   * used for private method "singleSPrinf"
   * @param iLength must large than -1
   * @param iDotLength dot length
   * @return Decimal Format . ex: ####.##
   * @throws Exception
   */
  private static String getDecimalFormat(boolean bThousandSeparate,int iLength,int iDotLength) throws Exception{
    String sReturn = "";
    int j=0;
    if( iDotLength>0) iLength = iLength-iDotLength-1;
    for(int i=iLength;i>0;i--){
      j++;
      sReturn = "#" + sReturn;
      if( j==3 && bThousandSeparate){
        sReturn ="," + sReturn ;
        j=0;
      };
    }
    if( sReturn.equals("") ) sReturn = "#";
    for(int i=0;i<iDotLength;i++)
      if( i==0 ) sReturn += ".0";
      else sReturn += "0";
    return sReturn;
  }

  public static void main(String a[]){
  /** test replace, addBackSlashes,stripBackSlashes*/
    try{
      System.out.println( addBackSlashes(replace("asdf'adsfddasdddddasd","asd","w")));
      System.out.println( addBackSlashes("asdf'adsfddasdddddasd"));
      System.out.println( stripBackSlashes(addBackSlashes("asdf'adsfddasdddddasd")));
    }catch(Exception ex){
    }
/*
    String sSqlStmt = "delete from AP_CULTRULE where EVENT=%s and PAYMONTH=%s and EMPTYPE=%s";
    java.util.Vector aVector = new java.util.Vector();
    aVector.add( new Double( "1111.111"));
    try{
      System.out.println(sprintf("%####.##d",aVector));
      System.out.println(singleSprintf("%'9.1f",new Double("1111.111")));
    }catch(Exception ex){};
*/
/*
    String aa = "insert into ORGUNIT (OID,ID,NAME,ULEVEL,TYPE," +
               "REFMANAGEDBY,REFUPPERUNIT,STATE,UPDATEDUSER,UPDATEDDT)  "+
               "values (%s,'%s','%s',%s,'%s',%s,%s,'%s','%s'";
    Vector aVector = new Vector();
    aVector.add("aa");
    aVector.add("aa");
    aVector.add("aa");
    aVector.add("aa");
    aVector.add("aa");
    aVector.add("aa");
    aVector.add("aa");
    aVector.add("aa");
    aVector.add((Object)"aa");
    try{
//      System.out.println( StringConverter.sprintf(aa,aVector) );
    //  System.out.println( insert("href=,href=","ref=","ddd") );
      Vector aVector = find("<a href=\"bbs_20021216174613.html\" >","<",">");
      for(int i=0;i<aVector.size();i++){
        System.out.println("ddd"+ aVector.get(i) );
      }
      System.out.println( replace("href=\"bbs_20021216174613.html\"","ref=\"","ref=\"ddd") );
    }catch(Exception ex){ex.printStackTrace();};  */
  }
}