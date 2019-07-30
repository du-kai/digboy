package org.smileboy.util;

import java.io.File;

public final class StringUtil {  
	  
    /** 
     * @Title: getMethodName 
     * @Description: ��ȡ�����������Ե�get������ 
     * @param propertyName 
     *            ������ 
     * @return String "get"��ͷ�Ҳ���(propertyName)ֵ����ĸ��д���ַ��� 
     */  
    public static String convertToReflectGetMethod(String propertyName) {  
        return "get" + toFirstUpChar(propertyName);  
    }  
  
    /** 
     * @Title: convertToReflectSetMethod 
     * @Description: ��ȡ�����������Ե�set������ 
     * @param propertyName 
     *            ������ 
     * @return String "set"��ͷ�Ҳ���(propertyName)ֵ����ĸ��д���ַ��� 
     */  
    public static String convertToReflectSetMethod(String propertyName) {  
        return "set" + toFirstUpChar(propertyName);  
    }  
  
    /** 
     * @Title: toFirstUpChar 
     * @Description: ���ַ���������ĸ��д 
     * @param target 
     *            Ŀ���ַ��� 
     * @return String ����ĸ��д���ַ��� 
     */  
    public static String toFirstUpChar(String target) {  
        StringBuilder sb = new StringBuilder(target);  
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));  
        return sb.toString();  
    }  
  
    /** 
     * @Title: getFileSuffixName 
     * @Description: ��ȡ�ļ�����׺ 
     * @param fileName 
     *            �ļ��� 
     * @return String �ļ�����׺���磺jpg 
     */  
    public static String getFileSuffixName(String fileName) {  
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();  
    }  
  
    /** 
     *  
     * @Title: checkStringIsNotEmpty 
     * @Description:��֤�ַ����Ƿ�Ϊ�� 
     * @param stringValue 
     *            ����Ҫ��֤���ַ��� 
     * @return boolean true����Ϊ �� �� ��Ϊnull; false:ֵΪ �� �� Ϊnull 
     */  
    public static boolean isNotEmpty(String stringValue) {  
        if (null == stringValue || "".equals(stringValue.trim())) {  
            return false;  
        }  
        return true;  
    }  
  
    /**    
     * @Title: getPackageByPath    
     * @Description ͨ��ָ���ļ���ȡ��ȫ��   
     * @param classFile ���ļ� 
     * @return String ��ȫ�� 
     */  
    public static String getPackageByPath(File classFile, String exclude){  
        if(classFile == null || classFile.isDirectory()){  
            return null;  
        }  
          
        String path = classFile.getAbsolutePath().replace('\\', '/');  
  
        path = path.substring(path.indexOf(exclude) + exclude.length()).replace('/', '.');  
        if(path.startsWith(".")){  
            path = path.substring(1);  
        }  
        if(path.endsWith(".")){  
            path = path.substring(0, path.length() - 1);  
        }  
  
        return path.substring(0, path.lastIndexOf('.'));  
    }  
}  
