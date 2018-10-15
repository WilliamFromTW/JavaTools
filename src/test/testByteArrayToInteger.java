package test;

import java.math.BigInteger;

import inmethod.commons.util.HexAndStringConverter;
import inmethod.commons.util.NotByteException;

public class testByteArrayToInteger {
	
	public static void main(String a[]) {
       try {
		byte[] bytes = HexAndStringConverter.convertHexStringToHexByte("ffffffffffffffffffff");
		double i = new BigInteger(1,bytes).doubleValue();
        System.out.println(i);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
}
