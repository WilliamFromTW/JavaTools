package inmethod.commons.util;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Enumeration;

/**
 * read system properties  *.properties (java style).
 * <pre>
 *   default locale is TRADITIONAL CHINESE
 * </pre>
 */
public class SystemConfig{
  private static final Locale DEFAULT_LOCALE = Locale.TRADITIONAL_CHINESE;

  private String sConfigFile ;
  private Locale aLocale;
  private ResourceBundle aResourceBundle;

  /**
   * config file be import like class
   */
  public SystemConfig(String sConfigFile){
    this(sConfigFile,DEFAULT_LOCALE);
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
}