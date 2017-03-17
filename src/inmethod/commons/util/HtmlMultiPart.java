package inmethod.commons.util;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * 寄email使用,可以寄html語法(含圖片).
 * @author william
 * @see inmethod.commons.util.MailTool
 */
public class HtmlMultiPart {

  private MimeBodyPart messageBodyPart = null;
  private MimeMultipart multipart = null;
  private Vector imageBodyPart = new Vector();
  private Vector attachmentBodyPart = new Vector();
  private String sDefaultCharset = null;

  /**
   * default is utf-8 encoded
   */
  public HtmlMultiPart() {
    this("utf-8");
  }

  /**
   *
   * @param sCharSet user define CharSet
   */
  public HtmlMultiPart(String sCharSet){
    sDefaultCharset = sCharSet;
    multipart = new MimeMultipart("related");
    imageBodyPart = new Vector();
    attachmentBodyPart = new Vector();
  }

  /**
   *
   * @param sHtmlCode String html code
   */
  public void setContent(String sHtmlCode){
    try{
      messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent(sHtmlCode,"text/html;charset=" + sDefaultCharset );
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

  /**
   *
   * @param sImgFileLocation String image file location
   * @param sContentID String ContentID(must match with sHtmlCode from setConetnt(sHtmlCode)
   */
  public void buildContentImage(String sImgFileLocation,String sContentID){
    try{
      DataSource fds = new FileDataSource(sImgFileLocation);
      MimeBodyPart aBP = new MimeBodyPart();
      aBP.setDataHandler(new DataHandler(fds));
      aBP.setHeader("Content-ID",sContentID);
      imageBodyPart.add(aBP);
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }
  public void buildAttachment(File file){
    try {
  	  MimeBodyPart aBP = new MimeBodyPart();
      aBP.attachFile( file);
      attachmentBodyPart.add(aBP);
	} catch (IOException ee){
	
	} catch(MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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
      for(int i=0;i<imageBodyPart.size();i++){
        multipart.addBodyPart( (MimeBodyPart)imageBodyPart.get(i) );
      }
      for(int i=0;i<attachmentBodyPart.size();i++){
        multipart.addBodyPart( (MimeBodyPart)attachmentBodyPart.get(i) );
      }
      return multipart;
    }catch(Exception ex){
      ex.printStackTrace();
      return null;
    }
  }
}