package inmethod.commons.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class TimeUtil {
	
	public static void main(String[] arg){
	  System.out.println( TimeUtil.getNTPDate("time.stdtime.gov.tw").getDate() );
	}
	
	/**
	 * Get network time .
	 * @param sNTPSite
	 * @return null if any error
	 */
	public static Date getNTPDate(String sNTPSite) {
		NTPUDPClient timeClient = new NTPUDPClient();
		InetAddress inetAddress;
		try {
			timeClient.setDefaultTimeout(10000);
			inetAddress = InetAddress.getByName(sNTPSite);
			TimeInfo timeInfo = timeClient.getTime(inetAddress);
			long returnTime = timeInfo.getMessage().getReceiveTimeStamp().getTime();
			return new Date(returnTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
