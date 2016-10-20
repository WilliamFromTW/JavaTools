package inmethod.commons.rdb;

import java.sql.*;
import inmethod.commons.NumberGen.NumberGen;
import java.util.*;
import inmethod.commons.util.Crypt;

/**
 * MS SQL 2000 JDBC連線.
 * @author william
 * @see inmethod.commons.rdb.JDBCConnection
 */
public class MS2kConnection extends JDBCConnection{
  private Properties aProp = null;
  private NumberGen aNumberGen = null;

  /**
   * @param sHost host name
   * @param sUID user id
   * @param sPWD user password
   * @param sDB database
   */
  public MS2kConnection(String sHost,String sUID,String sPWD,String sDB){
    this(sHost,sUID,sPWD,sDB,new NumberGen());
  }

  /**
   * @param sHost host name
   * @param sUID user id
   * @param sPWD user password
   * @param sDB database
   * @param aNumberGen user defined NumberGen
   */
  public MS2kConnection(String sHost,String sUID,String sPWD,String sDB,NumberGen aNumberGen){
      this.sClassForName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
      this.sURL = new String("jdbc:microsoft:sqlserver://" + sHost + ":1433;User=" + sUID + ";Password=" + sPWD + ";DatabaseName=" + sDB);
      this.sUID = new String(sUID);
      this.sPWD = new String(sPWD);
      this.aNumberGen = aNumberGen;
      this.sDatabaseIP = sHost;
  }

  /**
   * @param sHost host name
   * @param sUID user id
   * @param sPWD user password
   * @param sDB database
   * @param sCharSet
   */
  public MS2kConnection(String sHost,String sUID,String sPWD,String sDB,String sCharSet){
    this(sHost,sUID,sPWD,sDB,sCharSet,new NumberGen());
  }

  /**
   * @param sHost host name
   * @param sUID user id
   * @param sPWD user password
   * @param sDB database
   * @param sCharSet
   * @param aNumberGen user defined NumberGen
   */
  public MS2kConnection(String sHost,String sUID,String sPWD,String sDB,String sCharSet,NumberGen aNumberGen){
      this.sClassForName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
      this.sURL = new String("jdbc:microsoft:sqlserver://" + sHost + ":1433;User=" + sUID + ";Password=" + sPWD + ";DatabaseName=" + sDB);
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