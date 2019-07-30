package org.smileboy.util;

public class TypeCheckingUtil {
	
	public static boolean isBasic(Object obj){
		boolean isBasic = false;
		if(obj instanceof String ||
				obj instanceof Character ||
				obj instanceof Integer ||
				obj instanceof Double ||
				obj instanceof Long ||
				obj instanceof Boolean ||
				obj instanceof Float ){
			isBasic = true;
		}
		
		return isBasic;
	}
}
