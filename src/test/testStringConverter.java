package test;

import inmethod.commons.util.StringConverter;

public class testStringConverter {
	
	public static void main(String[] a) {
		String aa= " <a href='https://www.hlmt.com.tw'>test</a>";
		try {
			String a1 = StringConverter.replace(aa,"'","\\'");
			String a2 = StringConverter.replace(a1,"\\","\\\\");;
			System.out.println("a1="+a1+",a2="+a2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
