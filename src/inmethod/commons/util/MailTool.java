package inmethod.commons.util;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * send mail package.
 * default charset is utf-8
 * 
 * @author william
 * @see inmethod.commons.util.TextMultiPart
 * @see inmethod.commons.util.HtmlMultiPart
 */
public class MailTool {

	private static String sEncode = "utf-8";

	public static Session buildSession(String sMailServer) {
		if (sMailServer == null)
			return null;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", sMailServer);
		Session session = Session.getDefaultInstance(props, null);
		return session;
	}

	public static void setEncode(String s) {
		sEncode = s;
	}

	/**
	 *
	 * @param session
	 *            mail can use MailTool.buildSession to build mail session
	 * @param to
	 *            receiver's mail account
	 * @param cc
	 *            cc mail account
	 * @param bcc
	 *            bcc mail account
	 * @param from
	 *            mailler
	 * @param subject
	 *            subject
	 * @param bodytext
	 *            refer to HtmlMultiPart.class or TextMultiPart.class
	 * @param username
	 *            user name(not yet implement)
	 * @param password
	 *            password (not yet implemnet)
	 * @return boolean true: send ok false: send fail
	 * @see HtmlMultiPart , TextMultiPart
	 */
	public static boolean mailSend(Session session, Vector to, Vector cc, Vector bcc, String from, String subject,
			Multipart bodytext, String username, String password) {
		if (session == null || to == null || from == null || subject == null || bodytext == null)
			return false;

		String sTo = null, sCC = null, sBCC = null;
		if (to != null)
			for (int i = 0; i < to.size(); i++) {
				if (sTo == null)
					sTo = (String) to.get(i);
				else
					sTo = sTo + "," + (String) to.get(i);
			}
		if (cc != null)
			for (int i = 0; i < cc.size(); i++) {
				if (sCC == null)
					sCC = (String) cc.get(i);
				else
					sCC = sCC + "," + (String) cc.get(i);
			}
		if (bcc != null)
			for (int i = 0; i < bcc.size(); i++) {
				if (sBCC == null)
					sBCC = (String) bcc.get(i);
				else
					sBCC = sBCC + "," + (String) bcc.get(i);
			}
		session.setDebug(true);// open mail sesison debug function
		try {

			MimeMessage msg = new MimeMessage(session);
			// setup receiver
			InternetAddress address = new InternetAddress(from);
			String personal = address.getPersonal();
			if(personal != null) {
			  address.setPersonal(personal, "utf-8");
			}
			msg.setFrom(address);
			InternetAddress[] toAddress = InternetAddress.parse(sTo);
			msg.setRecipients(Message.RecipientType.TO, toAddress);
			if (sCC != null) {
				InternetAddress[] ccAddress = InternetAddress.parse(sCC);
				msg.setRecipients(Message.RecipientType.CC, ccAddress);
			}
			if (sBCC != null) {
				InternetAddress[] bccAddress = InternetAddress.parse(sBCC);
				msg.setRecipients(Message.RecipientType.BCC, bccAddress);
			}
			// subject
			msg.setSubject(subject, sEncode);
			// add the Multipart to the message
			msg.setContent(bodytext);
			// set the X-Mailer: header
			msg.setHeader("X-Mailer", "Java Mailer");

			// set the Date: header
			msg.setSentDate(new Date());


			// send the message
			Transport.send(msg);
			return true;
		} catch (Exception mex) {
			mex.printStackTrace();
			return false;
		}
	}

	/**
	 *
	 * @param a
	 *            not use.
	 * 
	 *            <pre>
	 *            public static void main(String[] a) {
	 *            	// to
	 *            	Vector to = new Vector();
	 *            	to.add("account@yourmailhost.com");
	 *            	// cc
	 *            	Vector cc = null;
	 *            	// bcc
	 *            	Vector bcc = null;
	 *            	// subject
	 *            	String subject = "hello Subject";
	 *            	// from
	 *            	String from = " senderaccount@yourmailhost.com ";
	 *            	// session
	 *            	Session session = MailTool.buildSession("smtpserver.com");
	 *            	// html with images
	 *            	HtmlMultiPart aHMP = new HtmlMultiPart();
	 *            	aHMP.setContent("&lt;h1&gt;hello&lt;/h1&gt;&lt;img src=\"cid:mememe\"&gt;");
	 *            	aHMP.buildContentImage("c:/mozilla.gif", "mememe");
	 *            	MailTool.mailSend(session, to, cc, bcc, from, subject, aHMP.getMultipart(), null, null);
	 *            	// text with attachment
	 *            	TextMultiPart aTMP = new TextMultiPart();
	 *            	aTMP.setContent("text hello");
	 *            	aTMP.buildAttachment("c:/mozilla.gif", "mozilla");
	 *            	MailTool.mailSend(session, to, cc, bcc, from, subject, aTMP.getMultipart(), null, null);
	 *            }
	 *            </pre>
	 */
	public static void main(String[] a) {
		/**
		 *  此兩行修正outlook無法顯示正常郵件名稱的問題, 必須在MultiPart進行編碼之前就設定, 否則無用
		 */
		System.setProperty("mail.mime.encodeparameters", "false"); 
		System.setProperty("mail.mime.encodefilename", "true");
		
		
		// to
		Vector to = new Vector();
		to.add("william.chen@hlmt.com.tw");
		// cc
		Vector cc = null;
		// bcc
		Vector bcc = null;
		// subject
		String subject = "你好";
		// from
		String from = "IT<it@hlmt.com.tw>";
		// session
		Session session = MailTool.buildSession("10.192.130.145");
		// html with images
		HtmlMultiPart aHMP = new HtmlMultiPart();
		aHMP.setContent("<h1>中文</h1><img src=\"cid:mememe\">");
		// aHMP.buildContentImage("c:/mozilla.gif","mememe");
        File file = new File("/opt/1111.pdf");
	    aHMP.buildAttachment(file,"1111.pdf");
		//System.setProperty("mail.mime.encodeparameters", "false");
		MailTool.mailSend(session, to, cc, bcc, from, subject, aHMP.getMultipart(), null, null);
		// text with attachment
		TextMultiPart aTMP = new TextMultiPart();
		aTMP.setContent("中文 hello");
		// aTMP.buildAttachment("c:/mozilla.gif","魔師啦");
		MailTool.mailSend(session, to, cc, bcc, from, subject, aTMP.getMultipart(), null, null);
	}

}