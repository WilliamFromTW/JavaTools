<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="inmethod_custom.Log" %>
<%
  // debug mode
  String sDebugMode = request.getParameter("DebugMode");
  String sDebugModeChecked = "";
  int iGlobalMode = 0;
  int iMode = 0;
  if( sDebugMode!=null && sDebugMode.equals( Integer.toString(Log.DEBUG_MODE) ) ){
    iMode += Log.DEBUG_MODE;
  }
  // warning mode
  String sWarningMode = request.getParameter("WarningMode");
  String sWarningModeChecked = "";
  if( sWarningMode!=null && sWarningMode.equals( Integer.toString(Log.WARNING_MODE) ) ){
    iMode += Log.WARNING_MODE;
  }
  // info mode
  String sInfoMode = request.getParameter("InfoMode");
  String sInfoModeChecked = "";
  if( sInfoMode!=null && sInfoMode.equals( Integer.toString(Log.INFO_MODE) ) ){
    iMode += Log.INFO_MODE;
  }
  // disable mode
  String sDisableMode = request.getParameter("DisableMode");
  String sDisableModeChecked = "";
  if( sDisableMode!=null && sDisableMode.equals( Integer.toString(Log.DISABLE_MODE) ) ){
    iGlobalMode = Log.DISABLE_MODE;
  }
  // normal mode
  String sNormalMode = request.getParameter("NormalMode");
  String sNormalModeChecked = "";
  if( sNormalMode!=null && sNormalMode.equals( Integer.toString(Log.NORMAL_MODE) ) ){
    iGlobalMode = Log.NORMAL_MODE;
  }
  if( iGlobalMode!=0 )
    Log.setGlobalMode( iGlobalMode );
  if( iMode!=0 )
    Log.setMode(iMode);
  /**
   *  get global mode
   */
  iGlobalMode = Log.getGlobalMode();
  iMode = Log.getMode();
  if( Log.isDebugMode(iMode) )
    sDebugModeChecked = "checked";
  if( Log.isWarningMode(iMode) )
    sWarningModeChecked = "checked";
  if( Log.isInfoMode( iMode ) )
    sInfoModeChecked = "checked";
  if( Log.isDisableMode( iGlobalMode ) )
    sDisableModeChecked = "checked";
  if( Log.isNormalMode( iGlobalMode ) )
    sNormalModeChecked = "checked";

%>
<html>
<head>
<script language=javascript>
  function disableOther(){
  //  document.form1.DebugMode.checked = false;
  //  document.form1.WarningMode.checked = false;
  //  document.form1.InfoMode.checked = false;
    document.form1.NormalMode.checked = false;

  }
  function normalMode(){
  //  document.form1.DebugMode.checked = false;
  //  document.form1.WarningMode.checked = false;
  //  document.form1.InfoMode.checked = false;
    document.form1.DisableMode.checked = false;
  }
  function disableDisableMode(){
  //  document.form1.DisableMode.checked = false;
  //  document.form1.NormalMode.checked = false;

  }
</script>
</head>
<body>
<form action="#" method="post" name=form1>
<center><h2><font color=red>SetUp GlobalMode</font></h2></center>
<input type=checkbox onclick='disableOther();' value='<%=Log.DISABLE_MODE%>' name=DisableMode <%=sDisableModeChecked%>>DISABLE_MODE<br>
<input type=checkbox onclick='normalMode();' value='<%=Log.NORMAL_MODE%>' name=NormalMode <%=sNormalModeChecked%>>NORMAL_MODE<br>
<center><h2><font color=red>SetUp Log Mode</font></h2></center>
<input type=checkbox onclick='disableDisableMode();' value='<%=Log.DEBUG_MODE%>' name=DebugMode <%=sDebugModeChecked%>>DEBUG_MODE<br>
<input type=checkbox onclick='disableDisableMode();' value='<%=Log.WARNING_MODE%>' name=WarningMode <%=sWarningModeChecked%>>WARNING_MODE<br>
<input type=checkbox onclick='disableDisableMode();' value='<%=Log.INFO_MODE%>' name=InfoMode <%=sInfoModeChecked%>>INFO_MODE<br>
</select>
<br>
<input type=submit>
</form>
</body>
</html>