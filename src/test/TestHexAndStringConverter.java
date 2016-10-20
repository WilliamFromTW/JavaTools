package test;

import inmethod.commons.util.HexAndStringConverter;
import inmethod.commons.util.NotByteException;

public class TestHexAndStringConverter {
   public static void main(String[] a){
	   try {
		System.out.println( HexAndStringConverter.checkBlueToothAddressBetweenStartAndEnd(  "12:00:00:00:00:AA","gg:00:00:00:00:00","12:00:10:00:00:00"));
	} catch (NotByteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
}
