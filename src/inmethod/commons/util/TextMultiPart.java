package inmethod.commons.util;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * mail body content.
 * default charset is utf-8
 * MessageBodyPart, use for text with attachment.
 * @author william chen
 * @see inmethod.commons.util.MailTool
 */
public class TextMultiPart {

  private MimeBodyPart messageBodyPart = null;
  private MimeMultipart multipart = null;
  private Vector fileBodyPart = null;
  private String sDefaultCharset = null;

  /**
   * default is utf-8 encoded
   */
  public TextMultiPart() {
    this("utf-8");
  }

  /**
   *
   * @param sCharSet user define
   */
  public TextMultiPart(String sCharSet){
    sDefaultCharset = sCharSet;
    multipart = new MimeMultipart();
    fileBodyPart = new Vector();
  }

  /**
   *
   * @param sTextCode text content
   */
  public void setContent(String sTextCode){
    try{
      messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText(sTextCode,sDefaultCharset);
    }catch(Exception ex){
    	 ex.printStackTrace();
    }
  }

  /**
   *
   * @param sFileLocation String file location
   * @param sFileName String file name
   */
  public void buildAttachment(String sFileLocation,String sFileName){
    try{
      MimeBodyPart mbp2 = new MimeBodyPart();
      // attach the file to the message
      FileDataSource fds = new FileDataSource(sFileLocation);
      mbp2.setDataHandler(new DataHandler(fds));
      // notice encode the file name to correct charset file name
      mbp2.setFileName( MimeUtility.encodeText(sFileName,sDefaultCharset,"B") );
      fileBodyPart.add(mbp2);
    }catch(Exception ex){
    	 ex.printStackTrace();
    }
  }

  /**
   *
   * @return get multipart
   */
  public Multipart getMultipart(){
    if( messageBodyPart == null ) return null;
    try{
      multipart.addBodyPart(messageBodyPart);
      for(int i=0;i<fileBodyPart.size();i++){
        multipart.addBodyPart( (MimeBodyPart)fileBodyPart.get(i) );
      }
      return multipart;
    }catch(Exception ex){
    	 ex.printStackTrace();
      return null;
    }
  }
}