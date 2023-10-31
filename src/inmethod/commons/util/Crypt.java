package inmethod.commons.util;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.util.Base64;
import java.util.Base64.*;

/**
 * 將字串以"MD5"或是"SHA"編碼.
 * 
 * @author william chen
 */
public class Crypt {

	public static void main(String a[]) {
		try {

			String s3DES = "DESede";
			// 設定欲加密的資料
			String sData = "ilovefromtw";
			String sData2 = null; // 預備存放解密之後的資料,然後與原始資料比對是否解密成功

			// 產生3DES的Secret key
			KeyGenerator keygen = KeyGenerator.getInstance(s3DES);
			SecretKey deskey = keygen.generateKey();

			// 使用Secret key 將資料加密之後取得加密後的資料
			Cipher c = Cipher.getInstance(s3DES);
			c.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] cipherByte = c.doFinal(sData.getBytes());

			// 使用Secret key 將加密的資料解開
			Cipher c1 = Cipher.getInstance(s3DES);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			sData2 = new String(c1.doFinal(cipherByte));

			System.out.println("原始資料 = " + sData);
			System.out.println("解密之後的資料 = " + sData2);

			String sEncodeData = "2,2,3,1,2,3,1,1,1,";
			String sSKEncode = "F85B2FA41C76DF4C8CA8D50EFE525D3D70709783E6C2689B";// Crypt.get3DESSecretKeyEncode();
			String s3DesEncode = "8BEC1A3A165F44070B293238EC5381786C342FCE1D845970E7E7FC399B3AA361E7E7FC399B3AA361C027C9C085D7E3D3A80D20D8B44DA9C457EEE0A04F6B0DE572C0106183D3BE5A";// Crypt.get3DESEncode(
			String s3DesDecode = Crypt.get3DESDecode(sSKEncode, s3DesEncode);
			System.out.println("encode data = " + sEncodeData);
			System.out.println("Secret key encode = " + sSKEncode);
			System.out.println("3 DES Encode  = " + s3DesEncode);
			System.out.println("3 DES Decode = " + s3DesDecode);

			System.out.println("source = dd,md5=" + MD5("dd"));
			String sEncryptByAES = Crypt.encryptByAES("hello world", "8dc532a4c96acbe0", "1292f260a52abdc4") ;
			System.out.println("Crypt.encryptByAES = " +sEncryptByAES );
			System.out.println("Crypt.dncryptByAES = " +Crypt.decryptByAES("bLBH87/TqktFpeIDTfcN/4SNzKJRlDs+0FHwq9m2qc/UqgDWOZ2yVAwkg6GiOfD1cVSw20yCTZDj/Xdp5f7lagaCZk4SuHeh6l9W11SN5wM=", "8dc532a4c96acbe0", "1292f260a52abdc4") );
			
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main2(String a[]) {
		try {
			String sTemp = null;
			sTemp = Crypt.DESEncode("asdf", "widlliam");
			System.out.println(sTemp + ",length=" + sTemp.length());
			System.out.println(Crypt.DESDecode("asdf", sTemp));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Crypt() {
	}

	/**
	 * 取得3DES 的 Secret key (然後取崆ecret key的Encode)
	 * 
	 * @return String Secret Key Encode
	 */
	public static String get3DESSecretKeyEncode() throws Exception {

		KeyGenerator keygen = KeyGenerator.getInstance("DESede");
		SecretKey deskey = keygen.generateKey();
		System.out.println("3des secret key encode =" + asHex(deskey.getEncoded()));
		return asHex(deskey.getEncoded());

	}

	/**
	 * 取得3DES 的 Secret key (然後取崆ecret key的Encode)
	 * 
	 * @return String Secret Key Encode
	 */
	public static String get3DESEncode(String sSecretKeyEncode, String sData) throws Exception {

		SecretKeySpec kk = new SecretKeySpec(asByte(sSecretKeyEncode), "DESede");
		Cipher c = Cipher.getInstance("DESede");
		c.init(Cipher.ENCRYPT_MODE, kk);
		byte[] cipherByte = c.doFinal(sData.getBytes());
		System.out.println("3des value=" + asHex(cipherByte));
		return asHex(cipherByte);

	}

	public static String get3DESDecode(String sSecretKeyEncode, String s3DESEncode) throws Exception {

		SecretKeySpec kk = new SecretKeySpec(asByte(sSecretKeyEncode), "DESede");
		Cipher c1 = Cipher.getInstance("DESede");
		c1.init(Cipher.DECRYPT_MODE, kk);
		System.out.println("decode = " + new String(c1.doFinal(asByte(s3DESEncode))));
		return new String(c1.doFinal(asByte(s3DESEncode)));
	}

	public static String DESEncode(String sEncodeKey, String sData) throws Exception {
		// 生成密鑰
		KeyGenerator keygen = KeyGenerator.getInstance("DES");
		SecretKeySpec kk = new SecretKeySpec(get8BitsKey(sEncodeKey).getBytes(), "DES");
		SecretKey deskey = keygen.generateKey();
		// 加密
		System.out.println("加密前的資料:" + sData + ",key=" + sEncodeKey);

		Cipher c1 = Cipher.getInstance("DES");
		c1.init(Cipher.ENCRYPT_MODE, kk);
		byte[] cipherByte = c1.doFinal(sData.getBytes());
		return asHex(cipherByte);
	}

	public static String DESDecode(String sEncodeKey, String sEncodeData) throws Exception {
		SecretKeySpec kk = new SecretKeySpec(get8BitsKey(sEncodeKey).getBytes(), "DES");
		// 解密
		Cipher c1 = Cipher.getInstance("DES");
		c1.init(Cipher.DECRYPT_MODE, kk);
		byte[] clearByte = c1.doFinal(asByte(sEncodeData));
		System.out.println("解密後的信息:" + (new String(clearByte)) + ",Key=" + sEncodeKey);
		return new String(clearByte);

	}

	/**
	 * encode Sting with md5
	 * 
	 * @param strParam String before encoded
	 * @return String after encoded, null: encode fail
	 */
	public static String MD5(String strParam) {
		byte[] aByte = null;

		try {
			MessageDigest aMD = MessageDigest.getInstance("MD5");
			aMD.update(strParam.getBytes());
			aByte = aMD.digest();
			return asHex(aByte);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		;
		return null;
	}

	/**
	 * Encode string with SHA
	 * 
	 * @param strParam String before encoded
	 * @return String after encoded, null: encode fail
	 */
	public static String SHA(String strParam) {
		byte[] aByte = null;
		try {
			MessageDigest aMD = MessageDigest.getInstance("SHA");
			aMD.update(strParam.getBytes());
			aByte = aMD.digest();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		;
		return null;
	}

	/**
	 * Bytes transfer to Hex String. ex: \u000D -> 0D
	 */
	private static String asHex(byte hash[]) {
		StringBuffer buf = new StringBuffer(hash.length * 2);
		int i;
		for (i = 0; i < hash.length; i++) {
			if (((int) hash[i] & 0xff) < 0x10)
				buf.append("0");
			buf.append(Long.toString((int) hash[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}

	private static byte[] asByte(String strhex) {
		if (strhex == null)
			return null;
		int l = strhex.length();
		if (l % 2 == 1)
			return null;
		byte[] b = new byte[l / 2];
		for (int i = 0; i < l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}

	/**
	 * If key length is less than 8, this will padding fit 8 bits
	 * 
	 * @param sKey String key
	 * @exception if sKey is null
	 */
	private static String get8BitsKey(String sKey) throws Exception {
		String sReturn = sKey;
		if (sKey.length() >= 8)
			return sKey.substring(0, 8);
		for (int i = sKey.length(); i < 8; i++) {
			sReturn = sReturn + "d";
		}

		return sReturn;

	}

	/**
	 * 
	 * @param content
	 * @param key  String length 16
	 * @param ivSeed String length 8
	 * @return
	 * @throws Exception
	 */
	   public static String  encryptByAES(String content, String key,String ivSeed) throws Exception {  
	        if (key == null) {  
              throw new Exception("null");  
	        }  
	        if (key.length() != 16) {  
	              throw new Exception("null");  
	        }  
	        if (ivSeed.length() != 16) {  
	              throw new Exception("null");  
	        }  
	        byte[] raw = key.getBytes("utf-8");  
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  //設置密鑰規範爲AES
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/補碼方式"
	        
	        //CBC模式需要配置偏移量，設置一個向量，達到密碼唯一性，增加加密算法的強度
	        IvParameterSpec iv = new IvParameterSpec(ivSeed.getBytes()); 
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  
	        byte[] encrypted = cipher.doFinal(content.getBytes());  
	  
	        return Base64.getEncoder().encodeToString(encrypted);
	    }
	   
	   /**
	    * 
	    * @param content
	 * @param key  String length 16
	 * @param ivSeed String length 8
	    * @return
	    * @throws Exception
	    */
	    public static String decryptByAES(String content, String key,String ivSeed) throws Exception {  
	        try {  
		        if (key == null) {  
		              throw new Exception("null");  
			        }  
			        if (key.length() != 16) {  
			              throw new Exception("null");  
			        }  
			        if (ivSeed.length() != 16) {  
			              throw new Exception("null");  
			        }  
	            byte[] raw = key.getBytes("ASCII"); //參數類型
	            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	       
	            IvParameterSpec iv = new IvParameterSpec(ivSeed.getBytes()); 
	            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);  
	            byte[] encrypted1 = Base64.getDecoder().decode(content);  
	            try {  
	                byte[] original = cipher.doFinal(encrypted1);  
	                String originalString = new String(original);  
	                return originalString;  
	            } catch (Exception e) {  
	                System.out.println(e.toString());  
	                return null;  
	            }  
	        } catch (Exception ex) {  
	            System.out.println(ex.toString());  
	            return null;  
	        }  
	    }	
}