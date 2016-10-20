package inmethod.commons.util;

import java.sql.*;
import java.net.*;
import java.util.*;
import java.lang.ClassNotFoundException;
import java.io.*;
import java.nio.charset.Charset;

/**
 * 一個TCP/IP Client Server 的Framework,可以用來開發自己的c/s系統. Using for writing your own
 * server processor.
 * 
 * @author william chen
 * @see inmethod.commons.util.Server
 */
abstract public class ServerProcessor implements Cloneable {

	private static int iSeed = 0;
	protected int iOID = -1;
	protected static List pool = new LinkedList();
	protected Socket connection = null;
	protected OutputStreamWriter out = null;
	protected InputStreamReader in = null;
	public static String sInputEncode = null;
	public static String sOutputEncode = null;
	public static final String SEND_CLOSE = "prince_william_close";
	public static final String SEND_TO = "prince_william_to";
	public static final String SEND_TO_CLOSE = "prince_william_to_close";
	public static final String SEND_BACK = "prince_william_back";
	public static final String SEND_ALL = "prince_william_all";
	public static final String SEND_BACK_CLOSE = "prince_william_back_close";
	public static final String SEND_ALL_CLOSE = "prince_william_all_close";

	/**
	 * default input encode and output encode is big5
	 */
	public ServerProcessor() {
		this("Big5", "Big5");
	}

	/**
	 * customize input and output encode
	 * 
	 * @param sInputEncode
	 *            input encode
	 * @param sOutputEncode
	 *            output encode
	 */
	public ServerProcessor(String sInputEncode, String sOutputEncode) {
		this.sInputEncode = sInputEncode;
		this.sOutputEncode = sOutputEncode;
	}

	public Socket getConnection() {
		return connection;
	}

	public void setConnection(Socket aConnection) {
		connection = aConnection;
	}

	/**
	 * initial object id to this object
	 */
	synchronized public void setOID() {
		iSeed++;
		iOID = iSeed;
	}

	/**
	 * return object id
	 */
	public int getOID() {
		return iOID;
	}

	/**
	 * this class implemnet clone method, and override protected method "clone"
	 * to public it will call method "init" first.
	 */
	public Object clone() throws CloneNotSupportedException {
		Object obj = super.clone();
		System.out.println("new Clone");
		((ServerProcessor) obj).init();
		((ServerProcessor) obj).setOID();
		return obj;
	};

	/**
	 * @return HashMap
	 * 
	 *         <pre>
	 *   Key=Cmd, vales will be SEND_ALL,SEND_BACK....
	 *   Key=Data, data
	 *   [optional] if key=SEND_TO, one key="OID" must be HashMap , value will be object id
	 *         </pre>
	 */
	public abstract HashMap parseCmd(int iCmd);

	/**
	 * initial this object data , called when clone be involke
	 */
	public abstract void init();

	/**
	 * close all resource in object
	 */
	public void destory() {
		try {
			deleteConnection();
			connection.close();
			connection = null;
			out = null;
			in = null;
			System.out.println("Object ServerProcessor destory");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		;
	}

	/**
	 * add socket connect to pool
	 */
	public boolean addConnection(Socket connection) {
		synchronized (pool) {
			pool.add(pool.size(), this);
			setConnection(connection);
			pool.notifyAll();
			System.out.println("Get connection,pool size=" + pool.size());
			return true;
		}
	}

	/**
	 * this socket connection in this object
	 */
	public boolean deleteConnection() {
		synchronized (pool) {
			pool.remove(this);
			pool.notifyAll();
			System.out.println("delete connection,pool size=" + pool.size());
		}
		try {
			connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	/**
	 * run.
	 */
	public void run() {
		HashMap aReturnHash = null;
		String sCmd = null;
		String sData = null;
		Socket aTmpConn = null;
		OutputStreamWriter aTmpOSW = null;
		if (true) {
			try {
				// raw = new BufferedOutputStream(connection.getOutputStream());
				// out = new OutputStreamWriter(raw,sOutputEncode);
				// in = new InputStreamReader(new
				// BufferedInputStream(connection.getInputStream()),sInputEncode);
				out = new OutputStreamWriter(connection.getOutputStream(), sOutputEncode);
				in = new InputStreamReader(connection.getInputStream(), sInputEncode);
				int c;
				System.out.println("output encode = " + out.getEncoding());
				System.out.println("input encode = " + in.getEncoding());
				while (true) {

					c = in.read();
					if (c == -1) {
						System.out.println("Connection has broken");
						if (aReturnHash != null)
							aReturnHash.clear();
						destory();
						return;
					}
					aReturnHash = parseCmd(c);
					if (aReturnHash != null) {
						sCmd = (String) aReturnHash.get("Cmd");
						sData = (String) aReturnHash.get("Data");
						if (sCmd.equals(SEND_BACK)) {
							out.write(sData);
							out.flush();
						} else if (sCmd.equals(SEND_BACK_CLOSE)) {
							out.write(sData);
							out.flush();
							if (aReturnHash != null)
								aReturnHash.clear();
							destory();
							return;
						} else if (sCmd.equals(SEND_ALL)) {
							for (int i = 0; i < pool.size(); i++) {
								aTmpConn = ((ServerProcessor) pool.get(i)).getConnection();
								aTmpOSW = new OutputStreamWriter(aTmpConn.getOutputStream(), sOutputEncode);
								aTmpOSW.write(sData);
								aTmpOSW.flush();
							}
						} else if (sCmd.equals(SEND_ALL_CLOSE)) {
							for (int i = 0; i < pool.size(); i++) {
								aTmpConn = ((ServerProcessor) pool.get(i)).getConnection();
								aTmpOSW = new OutputStreamWriter(aTmpConn.getOutputStream(), sOutputEncode);
								aTmpOSW.write(sData);
								aTmpOSW.flush();
							}
							if (aReturnHash != null)
								aReturnHash.clear();
							destory();
							return;
						} else if (sCmd.equals(SEND_TO)) {
							int iTmpOID = Integer.parseInt((String) aReturnHash.get("OID"));
							for (int i = 0; i < pool.size(); i++) {
								if (iTmpOID == ((ServerProcessor) pool.get(i)).getOID()) {
									aTmpConn = ((ServerProcessor) pool.get(i)).getConnection();
									aTmpOSW = new OutputStreamWriter(aTmpConn.getOutputStream(), sOutputEncode);
									aTmpOSW.write(sData);
									aTmpOSW.flush();
									break;
								}
							}
						} else if (sCmd.equals(SEND_TO_CLOSE)) {
							int iTmpOID = Integer.parseInt((String) aReturnHash.get("OID"));
							for (int i = 0; i < pool.size(); i++) {
								if (iTmpOID == ((ServerProcessor) pool.get(i)).getOID()) {
									aTmpConn = ((ServerProcessor) pool.get(i)).getConnection();
									aTmpOSW = new OutputStreamWriter(aTmpConn.getOutputStream(), sOutputEncode);
									aTmpOSW.write(sData);
									aTmpOSW.flush();
									break;
								}
							}
							if (aReturnHash != null)
								aReturnHash.clear();
							destory();
							try {
								if (!aTmpConn.isClosed())
									aTmpConn.close();
							} catch (Exception ee) {
							}
							return;
						} else if (sCmd.equals(SEND_CLOSE)) {
							out.flush();
							if (aReturnHash != null)
								aReturnHash.clear();
							destory();
							try {
								if (!aTmpConn.isClosed())
									aTmpConn.close();
							} catch (Exception ee) {
							}
							return;
						}
					}
				}
			} catch (Exception e) {
				destory();
				e.printStackTrace();
			}
		} // end of while
	}

}