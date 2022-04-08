package inmethod.commons.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import inmethod.commons.rdb.DataSet;

public class JsonUtil {

	
	public static final JsonObject converToJsonObject(String sColumnName,String sVallue) {
		JsonObject aReturn = new JsonObject();
		aReturn.addProperty(sColumnName, sVallue);
		return aReturn;
	}

	public static final JsonArray ResultSetToJsonArray(ResultSet rs) {
		return ResultSetToJsonArray(rs,false);
	}
	
	public static final JsonArray ResultSetToJsonArray(ResultSet rs, boolean bFormated) {
		JsonObject element = null;
		JsonArray ja = new JsonArray();

		ResultSetMetaData rsmd = null;
		String columnName, columnValue = null;
		try {
			rsmd = rs.getMetaData();
			while (rs.next()) {
				element = new JsonObject();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					//columnName = rsmd.getColumnName(i + 1);
					columnName = rsmd.getColumnLabel(i + 1);
					//System.out.println("col name="+columnName+",lable name="+ rsmd.getColumnLabel( i + 1));
					columnValue = rs.getString(columnName);
					if (columnValue == null)
						columnValue = "";
					if (bFormated)
						try {
							element.addProperty(getFormattedName(columnName), columnValue);
						} catch (Exception e) {
							e.printStackTrace();
							element.addProperty(columnName, columnValue);
						}
					else
						element.addProperty(columnName, columnValue);
				}
				ja.add(element);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ja;
	}

	public static final JsonObject ResultSetToJsonObject(ResultSet rs) {
		return ResultSetToJsonObject(rs, false);
	}

	public static final JsonObject ResultSetToJsonObject(ResultSet rs, boolean bFormated) {
		JsonObject element = null;
		JsonArray ja = new JsonArray();
		JsonObject jo = new JsonObject();
		ResultSetMetaData rsmd = null;
		String columnName, columnValue = null;
		try {
			rsmd = rs.getMetaData();
			String[] sCompareString = new String[rsmd.getColumnCount()];
			boolean bFirst = true;
			while (rs.next()) {
				element = new JsonObject();
				if (bFirst) {
					for (int i = 0; i < rsmd.getColumnCount(); i++)
						sCompareString[i] = "";
					bFirst = false;
				}
				boolean bTheSame = false;
				boolean bTempTheSame = true;
				for (int i = 0; i < rsmd.getColumnCount(); i++) {

					//columnName = rsmd.getColumnName(i + 1);
					columnName = rsmd.getColumnLabel(i + 1);

					//System.out.println("col name="+columnName+",lable name="+ rsmd.getColumnLabel( i + 1));
					columnValue = rs.getString(columnName);
					if (columnValue == null)
						columnValue = "";
					if (bTempTheSame && columnValue.equals(sCompareString[i])) {
						bTheSame = true;
					} else
						bTempTheSame = false;
					sCompareString[i] = columnValue;
					if (bTheSame)
						columnValue = "";
					else {
						// System.out.println("columnValue"+columnValue);
						columnValue = HTMLConverter.nl2br(columnValue);
					}
					bTheSame = false;
					if (bFormated)
						try {
							element.addProperty(getFormattedName(columnName), columnValue);
						} catch (Exception e) {
							e.printStackTrace();
							element.addProperty(columnName, columnValue);
						}
					else
						element.addProperty(columnName, columnValue);
				}
				ja.add(element);
			}
			jo.add("Data", ja);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jo;
	}

	public static final String ResultSetToJsonArrayString(ResultSet rs, boolean bFormated) {
		return ResultSetToJsonArray(rs, bFormated).toString();
	}

	public static final String ResultSetToJsonArrayString(ResultSet rs) {
		return ResultSetToJsonArray(rs).toString();
	}

	public static final String ResultSetToJsonString(ResultSet rs, boolean bFormated) {
		return ResultSetToJsonObject(rs, bFormated).toString();
	}

	public static final String ResultSetToJsonString(ResultSet rs) {
		return ResultSetToJsonObject(rs).toString();
	}

	/**
	 * convert Name to Uppercase name , ex: fieLd1_next -> Field1Next , fIELD =>
	 * Field.
	 * 
	 * @param sName
	 * @return String if null return {"Data":[]}
	 * @throws Exception
	 */
	private static String getFormattedName(String sName) throws Exception {
		if (sName == null || sName.trim().equals(""))
			throw new Exception("name is blank");
		StringBuffer sSB = new StringBuffer(sName.toLowerCase());
		int i = 0, j;
		while ((i = sSB.toString().indexOf("_", i)) != -1) {
			i++;
			sSB.replace(i, i + 1, sSB.toString().substring(i, i + 1).toUpperCase());
			sSB.replace(i - 1, i, "");
		}
		String sReturn = sSB.toString().substring(0, 1).toUpperCase() + sSB.toString().substring(1);
		return sReturn;

	}
}
