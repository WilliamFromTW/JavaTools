package inmethod.commons.rdb;
import inmethod.commons.NumberGen.NumberGen;
import java.sql.*;
import inmethod.commons.util.Crypt;

/**
 * SqlLite file db 連線 (thin client).
 * @author william
 * @see inmethod.commons.rdb.JDBCConnection
 */
public class DerbyFilesDbConnection extends JDBCConnection{

  private NumberGen aNumberGen = null;

  /**
   * @param sHost Host Name
   * @param sDatabase database (orcl)
   * @param sUID user id
   * @param sPWD user password
   */
  public DerbyFilesDbConnection(String sDatabase) {
    this(sDatabase,new NumberGen());
  }

  /**
   * @param sDatabase database
   * @param aNumberGen user defined NumberGen
   */
  public DerbyFilesDbConnection(String sDatabase,NumberGen aNumberGen) {
    this.sClassForName = "org.apache.derby.jdbc.EmbeddedDriver";
    this.sURL = new String("jdbc:derby:" +sDatabase+";create=true");
    this.aNumberGen = aNumberGen;
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

  public static void main(String[] a){
    try{
      JDBCConnection aSL = new DerbyFilesDbConnection("c:\\temp\\derby");
  //    aSL.getConnection().createStatement().executeUpdate("create table test (name, occupation)");
      Connection aConn = aSL.getConnection();
      aConn.close();
      System.out.println("finished!");
    }catch(Exception ex){ex.printStackTrace(); }
  }
}