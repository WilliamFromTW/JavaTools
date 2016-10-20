<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.Connection " %>
<%@ page import="inmethod.commons.rdb.JDBCConnection" %>
<%@ page import="inmethod.commons.util.StringConverter" %>
<%
  String f_sCloseKey = request.getParameter("closekey");
  if( f_sCloseKey!=null && !f_sCloseKey.equals("") )
    JDBCConnection.close( f_sCloseKey );
%>
<%
  HashMap f_aPoolHM = JDBCConnection.getAllConnectionInfo();
  Set aKeySet = null;
  Iterator aIterator = null;
  Connection f_aKey = null;
  String strVal = "";
  aKeySet = f_aPoolHM.entrySet();
  aIterator = aKeySet.iterator();
  Map.Entry aME = null;
  String f_sValue = "<table bgcolor=\"#FFFFCC\" width='100%' border='1' cellspacing='2' cellpadding='2' align='center'><tr><td width='20%'>連線</td><td  width='70%'>呼叫者</td><td>連線狀態</td></tr>";
  while(aIterator.hasNext()) {
    aME = (Map.Entry)aIterator.next();
    f_aKey = (java.sql.Connection)aME.getKey();
    strVal = (String)aME.getValue();
    if( f_aKey.isClosed())
      f_sValue += "<tr><td>"+f_aKey+"</td><td>"+strVal+"</td><td>連線已關閉</td></tr>";
    else
      f_sValue += "<tr><td>"+f_aKey+"</td><td>"+strVal+"</td><td><a href='showConnectionUsage.jsp?closekey="+inmethod.commons.util.HTMLConverter.encode(strVal) +"'>按此關閉</a></td></tr>";
  }
  f_sValue += "</table>";
%>
<HTML>
<HEAD>
    <TITLE></TITLE>
</HEAD>
<BODY>
<%=f_sValue%>
<form>
<center><input type=submit value="refresh"></center>
</form>
</BODY>
</HTML>