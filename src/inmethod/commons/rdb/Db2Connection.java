package inmethod.commons.rdb;

import inmethod.commons.NumberGen.NumberGen;
import java.sql.*;
import java.util.Properties;
import inmethod.commons.util.Crypt;


/**
 * DB2 JDBC type2 連線,需配合db2 jdbc driver.
 * @author william
 * @see inmethod.commons.rdb.JDBCConnection
 */
public class Db2Connection extends JDBCConnection{

  private NumberGen aNumberGen = null;

  /**
   * 使用Type 2 連線.
   * @param sDbAliasName host name
   * @param sUID user id
   * @param sPWD user password
   */
  public Db2Connection(String sDbAliasName,String sUID,String sPWD){
    this(sDbAliasName,sUID,sPWD,new NumberGen());
  }

  /**
   * 使用Type 2 連線.
   * @param sDbAliasName host name
   * @param sUID user id
   * @param sPWD user password
   * @param aNumberGen user defined NumberGen
   */
  public Db2Connection(String sDbAliasName,String sUID,String sPWD,NumberGen aNumberGen){
      this.sClassForName = "COM.ibm.db2.jdbc.app.DB2Driver";
      this.sURL = new String("jdbc:db2:"+sDbAliasName);
      this.sUID = new String(sUID);
      this.sPWD = new String(sPWD);
      this.aNumberGen = aNumberGen;
  }


  /**
   * 使用Type4方式連線.
   * @param sHost host name
   * @param sUID user id
   * @param sPWD user password
   * @param sDB database
   */
  public Db2Connection(String sHost,String sUID,String sPWD,String sDB){
    this(sHost,sUID,sPWD,sDB,new NumberGen());
  }

  /**
   * 使用Type4方式連線.
   * @param sHost host name
   * @param sUID user id
   * @param sPWD user password
   * @param sDB database
   * @param aNumberGen user defined NumberGen
   */
  public Db2Connection(String sHost,String sUID,String sPWD,String sDB,NumberGen aNumberGen){
      this.sClassForName = "COM.ibm.db2.jdbc.net.DB2Driver";
      this.sURL = new String("jdbc:db2://"+sHost+"/"+sDB);
      this.sDatabaseIP = sHost;
      this.sUID = new String(sUID);
      this.sPWD = new String(sPWD);
      this.aNumberGen = aNumberGen;
  }


  /**
   * 取得資料庫的連線.
   * @return Connection 連線
   */
  protected Connection getConn() throws Exception {
    Class.forName(sClassForName);
    Properties sysProps = new Properties();
    sysProps.put("jdbc.drivers", sClassForName.toString());
//       sysProps.setProperty("LANGUAGE", "big5");
//       sysProps.setProperty("CHARSET","big5");
    sysProps.put("user",sUID);
    sysProps.put("password",sPWD);
    Connection conn = DriverManager.getConnection(sURL,sUID,sPWD);
    return conn;
  }

  /**
   * 取得唯一值.
   * @param sSystemName give a System name to catalog
   * @return String unique id
   */
  protected String getUniqueID(String sSystemName) throws Exception{
    return aNumberGen.getNextNumber(sSystemName);
  }


}