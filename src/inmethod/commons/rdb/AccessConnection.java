package inmethod.commons.rdb;

import java.sql.*;
import java.util.*;

/**
 * MS access server JDBC連線.
 * @author william
 * @see inmethod.commons.rdb.JDBCConnection
 */
public class AccessConnection  extends JDBCConnection{
	
  private Properties aProp = null;
  private String sFileLocation = null;

  public AccessConnection (String sFilePath){
      this.sFileLocation = sFilePath;
      this.sClassForName = "net.ucanaccess.jdbc.UcanaccessDriver";
      sURL = "jdbc:ucanaccess://"+sFileLocation+";memory=false";      
      System.out.println("sURL="+sURL);
  }

  /**
   * @return connection
   */
  public Connection getConn() throws Exception {
    Class.forName( sClassForName );
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
    return sFileLocation;
  }


}