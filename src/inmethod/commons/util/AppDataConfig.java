package inmethod.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * create config files ("xxx".properties) .
 *
 */
public class AppDataConfig {

	private File aAppDataConfigFile = null;

	private AppDataConfig() {
	}

	public AppDataConfig(String sFileName) {
		aAppDataConfigFile = new File(getAppDataPath() + File.separator + sFileName + ".properties");
		if (!aAppDataConfigFile.exists())
			try {
				aAppDataConfigFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void setKeyValue(String sKey, String sValue) {
		Properties aProperties = new Properties();
		FileWriter aFileWriter = null;//
		FileReader in;
		try {
			in = new FileReader(aAppDataConfigFile);
			aProperties.load(in);
			aProperties.put(sKey, sValue);
			aFileWriter = new FileWriter(aAppDataConfigFile);
			aProperties.store(aFileWriter, "WriteFromAppDataConfig");
			aFileWriter.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getKeyValue(String sKey) {
		Properties defaultProps = new Properties();
		FileReader in;
		try {
			in = new FileReader(aAppDataConfigFile);
			defaultProps.load(in);
			return defaultProps.getProperty(sKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private String getAppDataPath() {
		String sAppDataPath = System.getenv("APPDATA");
		if (sAppDataPath == null)
			return ".";
		else
			return sAppDataPath;
	}

	public static void main(String ss[]) {
		AppDataConfig aAppDataConfig = new AppDataConfig("test");
		aAppDataConfig.setKeyValue("dddd", "ddddvalue");
		aAppDataConfig.setKeyValue("asdf", "asdfvalue");
		aAppDataConfig.setKeyValue("test", "中文測試");

		System.out.println(aAppDataConfig.getKeyValue("dddd"));
		System.out.println(aAppDataConfig.getKeyValue("test"));
		aAppDataConfig.setKeyValue("臨時加上", "臨時加上中文測試");
		System.out.println(aAppDataConfig.getKeyValue("臨時加上"));
		aAppDataConfig.setKeyValue("臨時加上", "臨時加上修改資料");
		System.out.println(aAppDataConfig.getKeyValue("臨時加上"));

	}
}
