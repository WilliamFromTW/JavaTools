<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="inmethod.commons.rdb.*" %>
<%@ page import="inmethod.commons.util.*" %>
<%
  response.setContentType("text/html; charset=UTF-8");
  request.setCharacterEncoding("UTF-8");
  String sDbEncode = "UTF-8";
  HashMap aHM = JDBCConnection.getAllConnectionKind();
  Map.Entry aEntry = null;
  Iterator aIt = null; //
  String sKey = null;
  String sValue = null;
  String sBody = "";
  if( aHM!=null ){
   aIt = aHM.entrySet().iterator();
   while( aIt.hasNext() ){
      aEntry = (Map.Entry)aIt.next();
      sKey = (String)aEntry.getKey();
      sBody += "<a href='queryDB.jsp?JdbcName=" + java.net.URLEncoder.encode(sKey) + "'>" + aEntry.getValue() + "</a><br>\n";
     // sValue = (String)aEntry.getValue();
   }
 }
%>
    <%=sBody%>