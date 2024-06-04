package inmethod.commons.rdb;

import java.sql.*;
import inmethod.commons.NumberGen.NumberGen;
import java.util.*;
import inmethod.commons.util.Crypt;

/**
 * Mysql server JDBC連線.
 * @author william
 * @see inmethod.commons.rdb.JDBCConnection
 */
public class MysqlConnection extends JDBCConnection{
  private Properties aProp = null;
  private NumberGen aNumberGen = null;

  /**
   * @param sHost host name
   * @param sUID user id
   * @param sPWD user password
   * @param sDB database
   */
  public MysqlConnection(String sHost,String sUID,String sPWD,String sDB){
    this(sHost,sUID,sPWD,sDB,new NumberGen());
  }

  /**
   * @param sHost host name
   * @param sUID user id
   * @param sPWD user password
   * @param sDB database
   * @param aNumberGen user defined NumberGen
   */
  public MysqlConnection(String sHost,String sUID,String sPWD,String sDB,NumberGen aNumberGen){
    this(sHost,sUID,sPWD,sDB,aNumberGen,"3306");
  }
  /**
   * @param sHost host name
   * @param sUID user id
   * @param sPWD user password
   * @param sDB database
   * @param aNumberGen user defined NumberGen
   */
  public MysqlConnection(String sHost,String sUID,String sPWD,String sDB,NumberGen aNumberGen,String sPort){
      this.sClassForName = "com.mysql.cj.jdbc.Driver";
      this.sURL = new String("jdbc:mysql://"+sHost+":"+sPort+"/"+sDB+"?user="+sUID+"&password="+sPWD);
      this.sUID = new String(sUID);
      this.sPWD = new String(sPWD);
      this.sDatabaseIP = sHost;
      this.aNumberGen = aNumberGen;
      String sKey = "Mysql_connection_"+sHost+"_"+sDB+"_"+Crypt.MD5( sUID+"_"+sPWD);
  }

  /**
   * @param sHost host name
   * @param sUID user id
   * @param sPWD user password
   * @param sDB database
   * @param sCharSet
   */
  public MysqlConnection(String sHost,String sUID,String sPWD,String sDB,String sCharSet){
    this(sHost,sUID,sPWD,sDB,sCharSet,new NumberGen());
  }

  /**
   * @param sHost host name
   * @param sUID user id
   * @param sPWD user password
   * @param sDB database
   * @param sCharSet
   */
  public MysqlConnection(String sHost,String sUID,String sPWD,String sDB,String sCharSet,String sPort){
    this(sHost,sUID,sPWD,sDB,sCharSet,new NumberGen(),sPort);
  }

  /**
   * @param sHost host name
   * @param sUID user id
   * @param sPWD user password
   * @param sDB database
   * @param sCharSet
   * @param aNumberGen user defined NumberGen
   */
  public MysqlConnection(String sHost,String sUID,String sPWD,String sDB,String sCharSet,NumberGen aNumberGen){
    this(sHost,sUID,sPWD,sDB,sCharSet,aNumberGen,"3306");
  }

  /**
   * @param sHost host name
   * @param sUID user id
   * @param sPWD user password
   * @param sDB database
   * @param sCharSet
   * @param aNumberGen user defined NumberGen
   */
  public MysqlConnection(String sHost,String sUID,String sPWD,String sDB,String sCharSet,NumberGen aNumberGen,String sPort){
      this.sClassForName = "com.mysql.cj.jdbc.Driver";
      aProp = new Properties();
      aProp.put("characterEncoding", sCharSet);
      aProp.put("useUnicode", "TRUE");
      this.sURL = new String("jdbc:mysql://"+sHost+":"+sPort+"/"+sDB+"?user="+sUID+"&password="+sPWD);
      this.sUID = new String(sUID);
      this.sPWD = new String(sPWD);
      this.sDatabaseIP = sHost;
      this.aNumberGen = aNumberGen;
  }
  /**
   * @return connection
   */
  public Connection getConn() throws Exception {
    Class.forName( sClassForName ).newInstance();
    Connection conn = null;
    if( aProp!=null)
      conn = DriverManager.getConnection(this.sURL,aProp);
    else
      conn = DriverManager.getConnection(this.sURL);
    return conn;
  }

  /**
   * Get auto increasement number, depend by db.
   * <pre>
   * Use in Sql Statement
   * mysql ex:
   *   insert into table (OID) value (null);
   * oracle ex:
   *   insert into table (OID) value (1...n);
   * Return "null" if mysql.getUniqueID()
   * Return unique number if oracle.getUniqueID()
   * </pre>
   * @param sSystemName give a System name to catalog
   */
  protected String getUniqueID(String sSystemName) throws Exception{
    return aNumberGen.getNextNumber(sSystemName);
  }


}