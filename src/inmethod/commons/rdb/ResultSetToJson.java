package inmethod.commons.rdb;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import com.google.gson.*;

import inmethod.commons.util.HTMLConverter;
import inmethod.commons.util.JsonUtil;

/**
 * @deprecated use JsonUtil
 * @author Administrator
 *
 */
public class ResultSetToJson {
	
	public static final JsonArray ResultSetToJsonArray(ResultSet rs) {
	  return JsonUtil.ResultSetToJsonArray(rs,false);	
	}
	
	public static final JsonArray ResultSetToJsonArray(ResultSet rs,boolean bFormated) {
		return JsonUtil.ResultSetToJsonArray(rs, bFormated);
	}

	
	public static final JsonObject ResultSetToJsonObject(ResultSet rs) {
		return JsonUtil.ResultSetToJsonObjectSkipTheSameColValue(rs,false);
	}
	
	public static final JsonObject ResultSetToJsonObject(ResultSet rs,boolean bFormated) {
		return JsonUtil.ResultSetToJsonObjectSkipTheSameColValue(rs,bFormated);
	}

	public static final String ResultSetToJsonArrayString(ResultSet rs,boolean bFormated) {
		return ResultSetToJsonArray(rs,bFormated).toString();
	}
	public static final String ResultSetToJsonArrayString(ResultSet rs) {
		return ResultSetToJsonArray(rs).toString();
	}	

	public static final String ResultSetToJsonString(ResultSet rs,boolean bFormated) {
		return ResultSetToJsonObject(rs,bFormated).toString();
	}
	public static final String ResultSetToJsonString(ResultSet rs) {
		return ResultSetToJsonObject(rs).toString();
	}
}