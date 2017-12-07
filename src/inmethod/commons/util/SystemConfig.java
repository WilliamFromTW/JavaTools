package inmethod.commons.util;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

/**
 * read system properties  *.properties (java style).
 * properties is on class base package. 
 * <pre>
 *   default locale is TRADITIONAL CHINESE
 * </pre>
 */
public class SystemConfig{

  private String sConfigFile ;
  private Locale aLocale;
  private ResourceBundle aResourceBundle;

  private SystemConfig() {}
  
  /**
   * config file be import like class
   */
  public SystemConfig(String sConfigFile){
    this(sConfigFile,Locale.TAIWAN);
  }

  public SystemConfig(String sConfigFile,Locale aLocale){
    this.sConfigFile = sConfigFile;
    this.aLocale = aLocale;

      aResourceBundle = ResourceBundle.getBundle(this.sConfigFile,this.aLocale);
 
  }

  public String getValue(String sKey){
  
    return aResourceBundle.getString(sKey);
  }

  /**
   * @return Enumeration ResourceBundle.getKeys();
   */
  public Enumeration  getKeys(){
    return aResourceBundle.getKeys();
  }

  /**
   * get Config file name
   */
  public String getConfigFileName(){
    return this.sConfigFile;
  }

  /**
   * get locale
   */
  public Locale getConfigLocale(){
    return aResourceBundle.getLocale();
  }
  
  public static void main(String ar[]){
	System.out.println("asdf="+(new SystemConfig("test",Locale.TAIWAN)).getConfigLocale());  
  }
}