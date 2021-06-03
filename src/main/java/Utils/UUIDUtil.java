package Utils;

import java.util.UUID;

public class UUIDUtil {
	/*
	* 创建一个32位的独一无二ID
	* */
	public static String getUUID(){
		
		return UUID.randomUUID().toString().replaceAll("-","");
		
	}
	
}
