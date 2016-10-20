package inmethod.commons.rdb;
import inmethod.commons.NumberGen.NumberGen;
import java.sql.*;
import inmethod.commons.util.Crypt;

/**
 * Oracle server jdbc type 2連線 (thin client).
 * @author william
 * @see inmethod.commons.rdb.JDBCConnection
 */
public class OracleConnection extends JDBCConnection{

  private NumberGen aNumberGen = null;

  /**
   * @param sHost Host Name
   * @param sDatabase database (orcl)
   * @param sUID user id
   * @param sPWD user password
   */
  public OracleConnection(String sHost,String sDatabase,String sUID,String sPWD) {
    this(sHost,sDatabase,sUID,sPWD,new NumberGen());
  }

  /**
   * @param sHost Host Name
   * @param sDatabase database (orcl)
   * @param sUID user id
   * @param sPWD user password
   * @param aNumberGen user defined NumberGen
   */
  public OracleConnection(String sHost,String sDatabase,String sUID,String sPWD,NumberGen aNumberGen) {
    this.sClassForName = "oracle.jdbc.driver.OracleDriver";
    this.sURL = new String("jdbc:oracle:thin:@" +sHost + ":1521:" + sDatabase);
    this.sUID = new String(sUID);
    this.sPWD = new String(sPWD);
    this.aNumberGen = aNumberGen;
    this.sDatabaseIP = sHost;
    String sKey = "Oracle_connection_"+sHost+"_"+sDatabase+"_"+Crypt.MD5( sUID+"_"+sPWD);
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

  protected Connection getConn() throws Exception {
//        Properties sysProps = new Properties();
//        sysProps.put("jdbc.drivers", drivers.toString());
//        sysProps.setProperty("LANGUAGE", "zht_taiwan");
//        sysProps.setProperty("CHARSET","big5");
//        sysProps.put("user",sUser);
//        sysProps.put("password",sPassword);
//        con = DriverManager.getConnection(sURL,sysProps);

    Class.forName(sClassForName);
    return DriverManager.getConnection(sURL,sUID,sPWD);
  }

}