package inmethod.commons.util;
import java.io.*;
import java.util.*;

/**
 * 將檔案複製到某個目錄去,複製的時候可以指定轉換文字的編碼方式.
 * @author william chen
 */
public class FileTool{

  public static void fileCopy2File(File sourceFile, File destDir) throws Exception {
    try{
      InputStream in = new FileInputStream(sourceFile);
      //For Overwrite the file.
      OutputStream out = new FileOutputStream(destDir);
      int c;
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0){
        out.write(buf, 0, len);
      }
      in.close();
      out.close();
    }catch(Exception ex)  {ex.printStackTrace();}
  }

  /**
   * copy file to dest directory [binary mode]
   * @param sourceFile
   * @param destDir
   */
  public static void fileCopy2Dir(File sourceFile, File destDir) throws Exception {
    byte[] buffer = new byte[4096]; // You can change the size of this if you want.

    destDir.mkdirs(); // creates the directory if it doesn't already exist.

    File destFile = new File(destDir, sourceFile.getName());
    FileInputStream in = new FileInputStream(sourceFile);
    FileOutputStream out = new FileOutputStream(destFile);
    int len;
    int sum=0;
    while((len = in.read(buffer)) != -1) {
        out.write(buffer, 0, len);
        sum += len;
    }
    out.close();
    in.close();
  }



  /**
   * copy dir to dir  , some specify file will encode
   * @param aSourceDir source directory
   * @param aDestDir
   * @param aExtName file entend name that will be encode
   * @param aFileDontEncode file name that wont be encoded even thery r ascii file
   * @param sSourceEncode encode of source file (ext name must in aExtName)
   * @param sDestEncode encode of dest file
   *
   */
  public static void dirCopy2Dir(File aSourceDir, File aDestDir,Vector aExtName,Vector aFileDontEncode,String sSourceEncode,String sDestEncode) throws Exception {
    String sSourceDir = aSourceDir.getAbsolutePath();
    String sDestDir = aDestDir.getAbsolutePath();
    File aFiles[] = null;
    if( aSourceDir.isDirectory() ){
      aFiles = aSourceDir.listFiles();
      for(int i=0;i<aFiles.length;i++){
        if( aFiles[i].isDirectory() )
//          System.out.println(sDestDir + getNearestDir(aFiles[i].getPath()));
          dirCopy2Dir(aFiles[i],new File(sDestDir + getNearestDir(aFiles[i].getPath())),aExtName,aFileDontEncode,sSourceEncode,sDestEncode);
        else if(aFiles[i].isFile()){
          if( FileTool.FileDontEncode(aFileDontEncode,aFiles[i].getName()) )
            FileTool.fileCopy2Dir(aFiles[i],new File(sDestDir));
          else{
            if( FileTool.isAsciiFile(aExtName,aFiles[i].getName()) )
              FileTool.fileCopy2Dir(aFiles[i],new File(sDestDir),sSourceEncode,sDestEncode);
            else
              FileTool.fileCopy2Dir(aFiles[i],new File(sDestDir));
          }
        }
        else
          throw new Exception("Dir Copy Fail: file type error") ;
      }
    }
    else
      return ;
  }

  /**
   * copy dir to dir  , some specify file will encode
   * @param aSourceDir source directory
   * @param aDestDir
   * @param aExtName file entend name that will be encode
   * @param sSourceEncode encode of source file (ext name must in aExtName)
   * @param sDestEncode encode of dest file
   *
   */
  public static void dirCopy2Dir(File aSourceDir, File aDestDir,Vector aExtName,String sSourceEncode,String sDestEncode) throws Exception {
    String sSourceDir = aSourceDir.getAbsolutePath();
    String sDestDir = aDestDir.getAbsolutePath();
    File aFiles[] = null;
    if( aSourceDir.isDirectory() ){
      aFiles = aSourceDir.listFiles();
      for(int i=0;i<aFiles.length;i++){
        if( aFiles[i].isDirectory() )
//          System.out.println(sDestDir + getNearestDir(aFiles[i].getPath()));
          dirCopy2Dir(aFiles[i],new File(sDestDir + getNearestDir(aFiles[i].getPath())),aExtName,sSourceEncode,sDestEncode);
        else if(aFiles[i].isFile()){
          if( FileTool.isAsciiFile(aExtName,aFiles[i].getName()) )
            FileTool.fileCopy2Dir(aFiles[i],new File(sDestDir),sSourceEncode,sDestEncode);
          else
            FileTool.fileCopy2Dir(aFiles[i],new File(sDestDir));
        }
        else
          throw new Exception("Dir Copy Fail: file type error") ;
      }
    }
    else
      return ;
  }

  /**
   * copy dir to dir [binary mode]
   * @param aSourceDir
   * @param aDestDir
   */
  public static void dirCopy2Dir(File aSourceDir, File aDestDir) throws Exception {
    String sSourceDir = aSourceDir.getAbsolutePath();
    String sDestDir = aDestDir.getAbsolutePath();
    File aFiles[] = null;
    if( aSourceDir.isDirectory() ){
      aFiles = aSourceDir.listFiles();
      for(int i=0;i<aFiles.length;i++){
        if( aFiles[i].isDirectory() )
//          System.out.println(sDestDir + getNearestDir(aFiles[i].getPath()));
          dirCopy2Dir(aFiles[i],new File(sDestDir + getNearestDir(aFiles[i].getPath())));
        else if(aFiles[i].isFile()){
          FileTool.fileCopy2Dir(aFiles[i],new File(sDestDir));
        }
        else
          throw new Exception("Dir Copy Fail: file type error") ;
      }
    }
    else
      return ;
  }

  /**
   * copy file to dest dir with encode.
   * @param sourceFile
   * @param destDir
   * @param sSourceEncode encode of source file
   * @param sDestEncode encode of dest file
   */
  public static void fileCopy2Dir(File sourceFile, File destDir,String sSourceEncode,String sDestEncode) throws Exception {


    destDir.mkdirs(); // creates the directory if it doesn't already exist.

    File destFile = new File(destDir, sourceFile.getName());
    InputStreamReader in = null;
    OutputStreamWriter out = null;
    try{
    	
      in = new InputStreamReader( new FileInputStream(sourceFile),sSourceEncode);
    }catch(Exception ex){throw new Exception("get Reader fail");};

    try{
      out = new OutputStreamWriter( new FileOutputStream(destFile),sDestEncode);
    }catch(Exception ex){throw new Exception("get Writer fail");};

    int len;
    int sum=0;

    while((len = in.read()) != -1) {
        out.write(len);
        sum += len;
    }
    out.flush();
    out.close();
    in.close();
  }

  /**
   * copy file to dest dir with encode
   * @param sourceFile
   * @param sEncode encode of source file
   */
  public static String readFile(File sourceFile, String sEncode) throws Exception {

    StringBuffer aSB = new StringBuffer();
    InputStreamReader in = null;
    try{
      in = new InputStreamReader( new FileInputStream(sourceFile),sEncode);
    }catch(Exception ex){throw new Exception("get Reader fail");};

    int len;
    int sum=0;

    while((len = in.read()) != -1) {
        aSB.append(len);
    }
    in.close();
    return aSB.toString();
  }

  private static String getNearestDir(String sDir) throws Exception{
    StringTokenizer aST = new StringTokenizer(sDir,System.getProperty("file.separator"));
    String sNearestDir = null;
    while( aST.hasMoreTokens() ){
      sNearestDir = aST.nextToken();
    }
    if( sNearestDir!=null ) return System.getProperty("file.separator") + sNearestDir;
    else throw new Exception ("getNearestDir error");
  }

  private static boolean FileDontEncode(Vector aExtName,String s){
    if( aExtName==null ) return false;
    for(int i=0;i<aExtName.size();i++){
      System.out.println( aExtName.get(i) + "," + s);
//      if(s.toUpperCase().indexOf(((String) (aExtName.get(i))).toUpperCase())!=-1 )
      if( aExtName.get(i).equals(s))
        return true;
    }
    return false;
  }

  private static boolean isAsciiFile(Vector aExtName,String s){
    String sExt = null;
    String kk = null;
    StringTokenizer aST = new StringTokenizer(s,".");
    if( aExtName==null ) return false;
    while( aST.hasMoreTokens() ){
      sExt=aST.nextToken();
    }
    for(int i=0;i<aExtName.size();i++){
      if( (aExtName.get(i)).toString().equalsIgnoreCase(sExt) )
        return true;
    }
    return false;
  }
  
  /**
   * get file extension name.
   * @param file
   * @return
   */
  public static String getFileExtension(File file) {
      String fileName = file.getName();
      return getFileExtension(fileName);
  }

  /**
   * get file extension name.
   * @param file
   * @return
   */
  public static String getFileExtension(String fileName) {
      if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
      return fileName.substring(fileName.lastIndexOf(".")+1);
      else return "";
  }
  
  /**
   * main entry.
   * <pre>
   * param a[0] source directory
   * param a[1] dest directory
   * param a[2] source encode [optional]
   * param a[3] dest encode [optional]
   * param a[4]....a[n] indicate some file don't be encode even thery r ascii file
   * </pre>
   */
  public static void main(String[] a){
    try{
      Vector aExtName = new Vector();
      Vector aFileDontEncode = new Vector();
      aExtName.add("txt");
      aExtName.add("jsp");
      aExtName.add("html");
      aExtName.add("css");
      aExtName.add("js");
      aExtName.add("java");
      aExtName.add("xml");
      aExtName.add("per");
      aExtName.add("perl");
      aExtName.add("4gl");
      aExtName.add("sch");
      for(int i=4;i<a.length;i++){
        aFileDontEncode.add(a[i]);
      }
      if( a.length == 4 )
        FileTool.dirCopy2Dir(new File(a[0]),new File(a[1]),aExtName,a[2],a[3]);
      else if(a.length==2)
        FileTool.dirCopy2Dir(new File(a[0]),new File(a[1]));
      else if(a.length>4)
        FileTool.dirCopy2Dir(new File(a[0]),new File(a[1]),aExtName,aFileDontEncode,a[2],a[3]);


/*
      File aSrcFile = new File("c:/temp/a.txt");
      File aDestFile = new File("c:/t");
      File aSrcFile2 = new File("c:/temp/a.zip");
      File aDestFile2 = new File("c:/t");
      Vector aExtName = new Vector();
      aExtName.add("txt");
      FileTool.fileCopy2Dir(aSrcFile,aDestFile,"big5","utf-8");
      FileTool.fileCopy2Dir(aSrcFile2,aDestFile2);
      System.out.println( isAsciiFile(aExtName,"asfd.txt"));*/
    }catch(Exception ex){ex.printStackTrace();};
  }
}