package inmethod.commons.rdb;

import java.sql.*;
import inmethod.commons.NumberGen.NumberGen;
import inmethod.commons.util.Crypt;
/**
 * JDBC-ODBC java 內建的資料庫連線方式,用ODBC當媒介連線.
 * @author william
 * @see inmethod.commons.rdb.JDBCConnection
 */
public class JdbcOdbcConnection extends JDBCConnection {

  private NumberGen aNumberGen = null;

  /**
   * @param sDataSource
   * @param sUID user id
   * @param sPWD user password
   * @param aNumberGen user defined NumberGen
   */
  public JdbcOdbcConnection(String sDataSource,String sUID,String sPWD,NumberGen aNumberGen){
      this.sClassForName = "sun.jdbc.odbc.JdbcOdbcDriver";
      this.sURL = new String("jdbc:odbc:"+sDataSource+";UID="+sUID+";PWD="+sPWD);
      this.sUID = new String(sUID);
      this.sPWD = new String(sPWD);
      this.aNumberGen = aNumberGen;
  }

  /**
   * @param sDataSource
   * @param sUID user id
   * @param sPWD user password
   */
  public JdbcOdbcConnection(String sDataSource,String sUID,String sPWD){
    this(sDataSource,sUID,sPWD,new NumberGen());
  }


  /**
   * @param sDataSource
   */
  public JdbcOdbcConnection(String sDataSource){
    this(sDataSource,new NumberGen());
  }

  /**
   * @param sDataSource
   * @param aNumberGen user defined NumberGen
   */
  public JdbcOdbcConnection(String sDataSource,NumberGen aNumberGen){
    this.sClassForName = "sun.jdbc.odbc.JdbcOdbcDriver";
    this.sURL = new String("jdbc:odbc:"+sDataSource);
    this.aNumberGen = aNumberGen;
  }

  /**
   * @return connection
   */
  public Connection getConn() throws Exception {
    Class.forName(sClassForName);
    return DriverManager.getConnection(sURL);
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