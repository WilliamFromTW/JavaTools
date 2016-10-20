package inmethod.commons.rdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

/**
 * 抽象類別,連線到資料庫的類別都必須繼承此類別,此類別的功用是用來做後端資料庫連線管理.
 * @author william
 */
public abstract class JDBCConnection {

  /** Get unique ID */
  protected static long lUniqueID = 0;
  /** Jdbc connection information */
  protected String sClassForName = null;
  protected String sURL = null;
  protected String sUID = null;
  protected String sPWD = null;
  protected String sDatabaseIP = "";

  // Current supported database
  public static final String MYSQL =  "mysql";
  public static final String DB2 = "db2";
  public static final String ORACLE = "oracle";


  /**
   * 設定JDBC驅動類別.
   * @param sClassForName
   */
  public void setDriverClass(String sClassForName){
    this.sClassForName = sClassForName;
  }

  /**
   * 設定Connection URL .
   * @param sURL
   */
  public void setURL(String sURL){
    this.sURL = sURL;
  }

  /**
   * 設定連線帳號.
   * @param sUID
   */
  public void setUID(String sUID){
    this.sUID = sUID;
  }

  /**
   * 設定連線密碼.
   * @param sPWD
   */
  public void setPWD(String sPWD){
    this.sPWD = sPWD;
  }

  /**
   * 取得JDBC驅動類別.
   * @return String
   */
  public String getDriverClass(){
    return sClassForName;
  }

  /**
   * 取得Connection URL.
   * @return String
   */
  public String getURL(){
    return sURL;
  }

  /**
   * 取得帳號.
   * @return String
   */
  public String getUID(){
    return sUID;
  }

  /**
   * 取得密碼.
   * @return String
   */
  public String getPWD(){
    return sPWD;
  }


  /**
   *  取得連線.
   * @return connection
   */
  public Connection getConnection() throws Exception{
    return getConnection("no Caller");

  }

  /**
   * 取得連線,若給定一個呼叫者名稱,可以事後透過程式追蹤連線.
   * @param sCaller caller name(maybe filename)
   * @return connection
   */
  public Connection getConnection(String sCaller) throws Exception{
     if( sCaller == null ) throw new Exception("JNDIConnection.getConnection ,no sCaller");
     try{
        Connection conn = getConn();
        return conn;
     }
     catch(Exception ex){
    	 ex.printStackTrace();
       throw new Exception("connection fail");
     }
  }

  /**
   * 取得資料庫連線的所有table.
   * @param aConn
   * @return ResultSet null if got exception
   * <pre>
   * Each table description has the following columns:
   *
   * TABLE_CAT String => table catalog (may be null)
   * TABLE_SCHEM String => table schema (may be null)
   * TABLE_NAME String => table name
   * TABLE_TYPE String => table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
   * REMARKS String => explanatory comment on the table
   * TYPE_CAT String => the types catalog (may be null)
   * TYPE_SCHEM String => the types schema (may be null)
   * TYPE_NAME String => type name (may be null)
   * SELF_REFERENCING_COL_NAME String => name of the designated "identifier" column of a typed table (may be null)
   * REF_GENERATION String => specifies how values in SELF_REFERENCING_COL_NAME are created. Values are "SYSTEM", "USER", "DERIVED". (may be null)
   * Note: Some databases may not return information for all tables.
   * </pre>
   */
  public static ResultSet getAllTables(Connection aConn){
    ResultSet aRset = null;
    try{
      aRset = aConn.getMetaData().getTables(null,null,null,null);
    }catch(Exception ex){
    	 ex.printStackTrace();
    }
    return aRset;
  }


  /**
   * 繼承本類別需要實現此method,取得資料庫連線.
   * @return Connection connection
   */
  abstract Connection getConn() throws Exception;

  /**
   * 繼承本類別需要實現此method,取得唯一值.
   * @param sSystemName give a System name to catalog
   * @return String 唯一值.
   */
  abstract String getUniqueID(String sSystemName) throws Exception;

  private synchronized static String getID(){
    lUniqueID++;
    return ""+lUniqueID;
  }

  public String getDatabaseIP(){
    return sDatabaseIP;
  }


}