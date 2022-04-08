package inmethod.commons.rdb;

import javax.naming.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Hashtable;
import inmethod.commons.NumberGen.NumberGen;
import inmethod.commons.util.Crypt;

/**
 * 取得jndi連線資源.
 * ex: ic.lookup("jdbc/DBFM");
 * @author william
 */
public class JndiConnection extends JDBCConnection{
  private Context ctx = null;
  private DataSource ds = null;
  private String sContextName = null;
  private String UID = null;
  private String PWD = null;
  private String sIniCtxFactory = null;
  private NumberGen aNumberGen = null;

  /**
   * @param sContextName , ex: jdbc/DBFM
   * @param UID user name
   * @param PWD user password
   * @param sIniCtxFactory context factory provider
   */
  public JndiConnection(String sContextName,String UID,String PWD,String sIniCtxFactory) {
    this(sContextName,UID,PWD,sIniCtxFactory,new NumberGen());
  }

  /**
   * @param sContextName , ex: jdbc/DBFM
   * @param UID user name
   * @param PWD user password
   * @param sIniCtxFactory context factory provider
   * @param aNumberGen user defined NumberGen
   */
  public JndiConnection(String sContextName,String UID,String PWD,String sIniCtxFactory,NumberGen aNumberGen) {
    try{
       Hashtable prop = new Hashtable();
       if( sIniCtxFactory == null)// default ibm ap server
         prop.put(Context.INITIAL_CONTEXT_FACTORY,"com.ibm.ejs.ns.jndi.CNInitialContextFactory");
       else
         prop.put(Context.INITIAL_CONTEXT_FACTORY,sIniCtxFactory);
//       prop.put("LANGUAGE", "zht_taiwan");
//       prop.put("CHARSET","big5");

       Context ctx = new InitialContext(prop);
       ds = (DataSource)ctx.lookup(sContextName);
    }
    catch(Exception ex){
    	ex.printStackTrace();
    };
    this.sContextName = sContextName;
    this.UID = UID;
    this.PWD = PWD;
    this.sIniCtxFactory = sIniCtxFactory;
    this.aNumberGen = aNumberGen;
  }

  /**
   * @param sContextName Context Name , ex: jdbc/DBFM
   * @param UID user name
   * @param PWD user password
   */
  public JndiConnection(String sContextName,String UID,String PWD) {
    this(sContextName,UID,PWD,null,new NumberGen());
  }

  /**
   * get connection
   */
  public Connection getConn() throws Exception {
    return ds.getConnection(UID,PWD);
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