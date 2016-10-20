package inmethod.commons.util;


import java.util.Arrays;

public class CheckSum {
	
    public static byte xorOneByteCheckSum(byte[] data) {
        String xorData = new String(data.clone());
        return xorOneByteCheckSum(xorData);
    }

    public static byte xorOneByteCheckSum(String data) {
        char[] chars = data.toCharArray();

        return xorOneByteCheckSum(chars);
    }

    public static byte xorOneByteCheckSum(char[] data) {
        char[] chars=Arrays.copyOf(data, data.length);

        for (int i = 1; i < chars.length; i++) {
            chars[0] ^= chars[i];
        }
        return (byte) chars[0];
    }
    
}