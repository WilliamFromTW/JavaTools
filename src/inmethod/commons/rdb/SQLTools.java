package inmethod.commons.rdb;
import java.sql.*;
import java.util.*;
import javax.sql.RowSet;
import javax.sql.rowset.*;
import com.sun.rowset.*;
/**
 * 若要下sql指令請透過此類別執行.
 * <pre>
 * 當我們拿到一個db Connection的時候,可以利用此SQLTools下sql command,
 * 此方法可以避免寫錯指令,也可用來集中管理sql cmd的用法.
 * ps. 傳入的Connection, 請自行做try resource.
 * </pre>
 * @author william
 */
public class SQLTools {
  private static SQLTools aSQLTools= null; 
  public boolean bPrintLog = false;
  
  private SQLTools(){}
  
  public static SQLTools getInstance(){
	if( aSQLTools==null) aSQLTools = new SQLTools();
	return aSQLTools;
  }
  
  public void setPrintLog(boolean bPrintLog){
	this.bPrintLog = bPrintLog;  
  }
  
  /**
   * 將DataSet存到資料庫
   */
  public void insertVectorToDb(Connection aConn,String sTable,Vector aFieldsName,DataSet aDS){
    String sSqlCmd = "INSERT INTO "+sTable;
    String sSqlTemp = null;
    String sFields = null;
    
    try{
      for(int i=0;i<aFieldsName.size();i++){
        if( sFields==null)
          sFields = (String)aFieldsName.get(i);
        else
          sFields = sFields + ","+aFieldsName.get(i);
      }
      sFields = "("+ sFields + ")";
      sSqlCmd = sSqlCmd + " " + sFields + " values ";

      System.out.println(sSqlCmd);
      String sValues = null;
      Vector aTempRecord = null;
      
      if(aDS!=null)
        while (aDS.next()){
          sValues = null;
          aTempRecord = (Vector)aDS.getData();
          for(int i=0;i<aTempRecord.size();i++){
            if( sValues == null ){
              if( aTempRecord.get(i) instanceof Integer ||
                  aTempRecord.get(i) instanceof Double  ||
                  aTempRecord.get(i) instanceof Float
                )
                sValues =  (String)aTempRecord.get(i);
              else
                sValues =  "'" +aTempRecord.get(i)+"'";
            }
            else{
              if( aTempRecord.get(i) instanceof Integer ||
                  aTempRecord.get(i) instanceof Double  ||
                  aTempRecord.get(i) instanceof Float
                )
                sValues = sValues + "," +aTempRecord.get(i);
              else
                sValues = sValues + ",'" +aTempRecord.get(i)+"'";
            }
          }
          sSqlTemp = sSqlCmd + "(" + sValues + ")";

          try(Statement aStat = aConn.createStatement();){
            aStat.executeUpdate(sSqlTemp);
          }catch(Exception ex){ex.printStackTrace(); };
          System.out.println(sSqlTemp);
        }

    }catch(Exception ex){
    }
  }

   /**
    * 
    * update , delete and insert sql command
    */
   public  int Update(Connection aConn,String sSql) throws Exception{
     try(Statement stmt = aConn.createStatement()){
       return stmt.executeUpdate( sSql);
     }catch(Exception err){
       err.printStackTrace();
       throw new Exception("SQLTools->Update() Update fail");
     }//end of try-catch

   }

   /**
    * Do page select.
    * @param sSql sql statement
    * @param lRowsBegin   may start from 1,2,...
    * @param lPageRows  row number per page must >0
    * @return ResultSet<br>
    * @throws Exception if lPageRows <=0 or sql statement
    */
   public  ResultSet PageSelect(Connection aConn,String sSql,long lRowsBegin,long lPageRows) throws Exception{
       if(lPageRows<=0){System.out.println("lPageRows must >0 "); throw new Exception("SQLTools.PageSelect(),lPageRows must >0 ");};
       ResultSet aRS =  Select(aConn,sSql);
       long l=1;
       // which record
       while ((l <= lRowsBegin) && aRS.next())
           l++;
       return aRS;

   }

   /**
    * @param table table name
    * @param fields field name
    * @param condition  where condition
    * @param lRowsBegin   may start from 1,2,...
    * @param lPageRows  row number per page must >0
    * @return ResultSet<br>
    * @throws Exception if lPageRows <=0 or sql statement
    */
   public  ResultSet PageSelect(Connection aConn,String table,String fields,String condition,long lRowsBegin,long lPageRows) throws Exception{
       if(lPageRows<=0){System.out.println("lPageRows must >0 "); throw new Exception("SQLTools.PageSelect(),lPageRows must >0 ");};
       ResultSet aRS = Select(aConn,table,fields,condition);
       long l=1;
       // which record
       while ((l <= lRowsBegin) && aRS.next())
           l++;
       return aRS;
   }

   /**
    * Do query
    * @param sSql
    * @return ResultSet
    * @exception Exception if do sql statement error
   */
  public  ResultSet Select(Connection aConn,String sSql) throws Exception{
	  if( bPrintLog )      System.out.println("select ,sql = " + sSql);
      return aConn.createStatement().executeQuery(sSql);

  }

   /**
    * @param table table name
    * @param fields field name
    * @param condition Where condition , ex: " field1='a' "
    * @return ResultSet no data if null
    * @exception Exception if do sql statement error
    */
   public  ResultSet Select(Connection aConn,String table,String fields,String condition) throws Exception{
    String sSql = null;
    try{
      sSql = "Select "+fields+" from "+table+" where "+condition;
      return Select(aConn,sSql);
    }catch(Exception err){
      err.printStackTrace();
      throw new Exception(err.getMessage());
    }//end of try-catch

  }

 

  /**
   * INSERT
   * @param table  table to be inserted
   * @param fields  ex: " field1,field2,field3 "
   * @param values  ex: " 'a',1,'b' "
   * @return int Returncode after success doing insert command
   * @throws Exception  if go some exception or error
   */
   public  int Insert(Connection aConn,String table,String fields,String values)  throws Exception{
      String sSql = "Insert into "+table+" ("+fields+") values ("+values+")";
      if(bPrintLog) System.out.println("Insert,sql = " + sSql);
      int iReturnCode = -1;
      try(Statement stmt=aConn.createStatement()){
         iReturnCode = stmt.executeUpdate(sSql);
         return iReturnCode;
      }catch(Exception err){
        err.printStackTrace();
        System.out.println("insert fail!  statement = " + sSql + "\n" + err.getMessage());
        throw new Exception("insert fail");
      }
   }

  /**
   * DELETE
   * @param table  table name to be deleted
   * @param condition  condition after ' where ' , ex: " field1='a' "
   * @return int  return code after success doing conn.executeUpdate
   * @throws Exception  if got some exception
   */
   public  int Delete(Connection aConn,String table,String condition)  throws Exception{
      //delete the currently selected row
      String sSql = "Delete from "+table+" where "+condition;
      if(bPrintLog)  System.out.println("Delete , sql = " + sSql);
      int iReturnCode = -1;
      try(Statement stmt=aConn.createStatement()){
         iReturnCode = stmt.executeUpdate(sSql);
         return iReturnCode;
      }catch(Exception err){
        throw new Exception("Delete fail");
      }

  }

  /**
   * UPDATE
   * @param table  table to be updated
   * @param value  update value , ex:  " field1 = 'a' , field2 = a "
   * @param condition  condition after 'where' , ex: " field1 = 'a' order by field1 "
   * @return int  return code after do conn.executeUpdate
   * @throws Exception  if got some error
   */
   public  int Update(Connection aConn,String table,String value,String condition)  throws Exception{
      //update data
      String sSql = "Update "+table+" set "+value+" where "+condition;
      if(bPrintLog)  System.out.println("Update , sql = " + sSql);
      int iReturnCode = -1;
      try(Statement stmt=aConn.createStatement()){
         iReturnCode = stmt.executeUpdate(sSql);
         return iReturnCode;
      }catch(Exception err){
        throw new Exception("Update fail");
      }

   }

   /**
    * Update table using prepared statement.
    * @param strSqlStmt  ex: update * set field3=? where field1=? and field2=?
    * @param aValue  value order by "?" in strSqlStmt(value must be suitable data type)
    * @return ReturnCode
    * @throws Exception
    */
    public  int preparedUpdate(Connection aConn,String strSqlStmt, Vector aValue) throws Exception{
      int iReturnCode = -1;
      
      try(PreparedStatement stmt = aConn.prepareStatement(strSqlStmt);){
        int iSize = aValue.size();
        for (int i=0;i<iSize;i++){
          if( aValue.get(i)!= null )
            stmt.setObject(i+1,aValue.get(i));
          else
            stmt.setNull(i+1,java.sql.Types.NULL);
        }
        iReturnCode = stmt.executeUpdate();
        stmt.close();
        return iReturnCode;
      }catch(Exception se){
        se.printStackTrace();
        throw new Exception("SQLTools.preparedUpdate," + se.getMessage() );
      }
    }
    
   /**
    * Delete record(s) using prepared statement.
    * @param strSqlStmt ex: delete from table1 where field1=? and field2=?
    * @param aValue value order by "?" in strSqlStmt
    * @return ReturnCode
    * @throws Exception
    */
   public  int preparedDelete(Connection aConn,String strSqlStmt, Vector aValue) throws Exception{
     return preparedUpdate(aConn,strSqlStmt,aValue);
   }


   /**
    * Insert into table using prepared statement.
    * @param strSqlStmt ex: insert into table1 (field1,field2...) values (?,?,..)
    * @param aValue value order by "?" in strSqlStmt
    * @return ReturnCode
    * @throws Exception
    */
   public  int preparedInsert(Connection aConn,String strSqlStmt, Vector aValue) throws Exception{
     return preparedUpdate(aConn,strSqlStmt,aValue);
   }


}