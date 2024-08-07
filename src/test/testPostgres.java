package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import inmethod.commons.NumberGen.NumberGen;
import inmethod.commons.rdb.MysqlConnection;
import inmethod.commons.rdb.PostgresConnection;

public class testPostgres {
	
	public static void main(String[] ar) {
	    
		String SQL_HOST = "10.192.130.145";
		String SQL_UID = "root";
		String SQL_PWD = "onepizza";
		String SQL_DB = "openproject";
		String SQL_PORT = "5433";
	    try(Connection aConn = (new  PostgresConnection(SQL_HOST,SQL_UID,SQL_PWD,SQL_DB,"UTF-8",SQL_PORT)).getConnection();){
	    	ResultSet aRS  = aConn.createStatement().executeQuery("select * from work_packages");
	    	if(aRS!=null)
	    		while( aRS.next()) {
	    			System.out.println("subject = " + aRS.getString("subject"));
	    		}
	    	
	    	
	    }catch(Exception ee) {
	    	ee.printStackTrace();
	    }
	}

}
