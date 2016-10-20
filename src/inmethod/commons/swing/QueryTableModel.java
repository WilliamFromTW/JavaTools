package inmethod.commons.swing;
import java.sql.*;
import java.io.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

public class QueryTableModel extends AbstractTableModel {
  Vector cache;  // will hold String[] objects . . .
  int colCount;
  String[] headers;
  String[] colType;
  Connection db;
  String currentURL;

  public QueryTableModel() {
    cache = new Vector();
 //   new gsl.sql.driv.Driver();
  }

  public String getColumnName(int i) { return headers[i]; }
  public int getColumnCount() { return colCount; }
  public int getRowCount() { return cache.size();}

  public Object getValueAt(int row, int col) {
    Object aO =   ((Object [])cache.elementAt(row))[col];
      return aO;
  }

  // All the real work happens here; in a real application,
  // we'd probably perform the query in a separate thread.
  public void setQuery(String q) {
    cache = new Vector();
    Statement statement = null;
    try {
      // Execute the query and store the result set and its metadata
      statement = db.createStatement();
      ResultSet rs = statement.executeQuery(q);
      ResultSetMetaData meta = rs.getMetaData();
      colCount = meta.getColumnCount();

      // Now we must rebuild the headers array with the new column names
      headers = new String[colCount];
      colType = new String[colCount];
      for (int h=1; h <= colCount; h++) {
        headers[h-1] = meta.getColumnName(h);

        colType[h-1] = getDataType(meta.getColumnType(h),meta.getScale(h) );
      }

      // and file the cache with the records from our query.  This would not be
      // practical if we were expecting a few million records in response to our
      // query, but we aren't, so we can do this.
      while (rs.next()) {
        Object[] record = new Object[colCount];
        for (int i=0; i < colCount; i++) {
           if( colType[i].equals("Double") )
            record[i] = new Double(rs.getDouble(i + 1));
           else if( colType[i].equals("Integer") )
            record[i] = new Integer(rs.getInt(i + 1));
           else
            record[i] = rs.getString(i + 1);
        }
        cache.addElement(record);
      }
      fireTableChanged(null); // notify everyone that we have a new table.

    }
    catch(Exception e) {
      cache = new Vector(); // blank it out and keep going.
      e.printStackTrace();
    }
    finally{
      try{
        if( statement!=null)
          statement.close();
      }catch(Exception ex){ex.printStackTrace();}
    }
  }
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

  public void setConnection(Connection aConn) {
    try {
      db = aConn;
    }
    catch(Exception e) {
      System.out.println("Could not initialize the database.");
      e.printStackTrace();
    }
  }

  /**
   * convert iSqlType to String
   * @param iSqlType
   * @return String
   */
  private String getDataType(int iSqlType,int i){
    String strObjectType = null;
    switch(iSqlType){
      // suppose bigint, integer , tinyint to be Integer
      case java.sql.Types.BIGINT:
      case java.sql.Types.TINYINT:
      case java.sql.Types.INTEGER:
        strObjectType = "Integer";
        break;
      case java.sql.Types.NUMERIC:
      case java.sql.Types.FLOAT:
      case java.sql.Types.DOUBLE:
      case java.sql.Types.DECIMAL:
      case java.sql.Types.REAL:
        if(i>0)
          strObjectType = "Double";
        else
          strObjectType = "Integer";
        break;
      // char,varbinary,date,varchar to String
      case java.sql.Types.CHAR:
      case java.sql.Types.VARBINARY:
      case java.sql.Types.VARCHAR:
      case java.sql.Types.DATE:
      case java.sql.Types.TIMESTAMP:
        strObjectType = "String";
        break;
      default:
        strObjectType = "String";
        break;
    }
    return strObjectType;
  }

}