<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="inmethod.commons.rdb.*" %>
<%@ page import="inmethod.commons.util.*" %>
<%
  String sJdbcConnectionString = request.getParameter("JdbcName");
  String sHidden = "<input type=hidden name=QueryType><input type=hidden name=JdbcName value='" + sJdbcConnectionString + "'>";
  String sTable = request.getParameter("Table") ;
  String sSQL = request.getParameter("SQL");
  if( sSQL==null) sSQL="";
  String sQueryType = request.getParameter("QueryType");
  String sCurrentPageNumber = request.getParameter("Page");
  String sSqlString = "";
  String sTableOption = "";
  StringBuffer sOutput1 = new StringBuffer();
  StringBuffer sOutput2 = new StringBuffer();
  StringBuffer sOutput3 = new StringBuffer();
  /** 如果有接到參數 */
  SQLCommand aSqlCmd = null;
  DatabaseMetaData metaData = null;
  ResultSet aDataRset = null;
  ResultSet aTablesRset = null;
  Connection aConn = null;
  ResultSetMetaData aRsetMetaData = null;
  String sFieldName = "";
  int iFieldCount = 0;
  sOutput2.append("<tr bgcolor=green>");
  try{
    if( JDBCConnection.getConnectionKind(sJdbcConnectionString)!=null ){
      aSqlCmd = new SQLCommand( JDBCConnection.getConnectionKind(sJdbcConnectionString).getConnection());
      aConn = aSqlCmd.getConnection();
      aTablesRset = JDBCConnection.getAllTables(aConn);
    }
    if( aTablesRset!=null )
      while( aTablesRset.next() ){
        if( sTable!=null && sTable.trim().equals( aTablesRset.getString("TABLE_NAME").trim() ))
          sTableOption += "<option value='" + aTablesRset.getString("TABLE_NAME") + "' selected>" + aTablesRset.getString("TABLE_TYPE") + "-" + aTablesRset.getString("TABLE_NAME") + "</option>";
        else
          sTableOption += "<option value='" + aTablesRset.getString("TABLE_NAME") + "'>" + aTablesRset.getString("TABLE_TYPE") + "-" + aTablesRset.getString("TABLE_NAME") + "</option>";
      }
    if( sQueryType.equalsIgnoreCase("TABLE") ){

        sSqlString = "select * from " + sTable;
        aDataRset = aSqlCmd.Select(sTable,"*","1=1");
        aRsetMetaData = aDataRset.getMetaData();
        aDataRset = aConn.createStatement().executeQuery(sSqlString);
        aRsetMetaData = aDataRset.getMetaData();
        if( aDataRset!=null ){
//          aDataRset.beforeFirst();
          while(aDataRset.next()){

            sOutput3.append("<tr bgcolor=yellow>");
            for(int i=0;i<aRsetMetaData.getColumnCount();i++){
              sOutput3.append("<td>" + aDataRset.getString((i+1)) + "</td>");
            }
            sOutput3.append("</tr>");
          }

        }
    }
    else{
        sSqlString = StringConverter.stripBackSlashes( sSQL );
        aConn = aSqlCmd.getConnection();
        if( sSQL.toUpperCase().indexOf("SELECT")!=-1 ){
          aDataRset = aConn.createStatement().executeQuery(sSqlString);

          aRsetMetaData = aDataRset.getMetaData();
          if( aDataRset!=null ){
//            aDataRset.beforeFirst();
            while(aDataRset.next()){
              sOutput3.append("<tr bgcolor=yellow>");
              for(int i=0;i<aRsetMetaData.getColumnCount();i++){
                sOutput3.append("<td>" +  aDataRset.getString((i+1)) + "</td>");
              }
              sOutput3.append("</tr>");
            }
          }
        }
        else{
          aConn.createStatement().executeUpdate(sSqlString);
        }
    }
    if( aRsetMetaData!=null )
      for(int i=0;i<aRsetMetaData.getColumnCount();i++){
        sOutput2.append("<td>" + aRsetMetaData.getColumnLabel(i+1) + "</td>");
      }
  }catch(Exception ex){
     Log.printException(ex);
  }
  finally{
    if( aSqlCmd!=null )
      aSqlCmd.closeConnection();
    sOutput2.append("</tr>");
  }
%>
<HTML>
<head>
<script language="javascript">
  function doTableQuery(){
    document.form1.QueryType.value="TABLE";
  	document.form1.action="queryDB.jsp";
    document.form1.submit();
  }
  function doSQLQuery(){
    if( document.form1.SQL.value==null || document.form1.SQL.value==""){
      alert("Please Input SQL statement!");
      return;
    }
    document.form1.QueryType.value="SQL";
  	document.form1.action="queryDB.jsp";
    document.form1.submit();

  }
</script>
</head>
<body>
<form name=form1 method=post >
Table : <Select name=Table><%=sTableOption%></Select>  <input type=button onclick="doTableQuery();" value="Table查詢"><br>
SQL   : <textarea rows="7" cols=40% name=SQL><%=sSQL%></TextArea>  <input type=button onclick="doSQLQuery();" value="SQL查詢"><br>
<hr>
<p color=blue>
SQL 條件: <%=sSqlString%></p>
<hr>
<br>
<table border=1>
<%=sOutput1%>
<%=sOutput2%>
<%=sOutput3%>
<%=sHidden%>
</table>
</form>
</body>
</html>