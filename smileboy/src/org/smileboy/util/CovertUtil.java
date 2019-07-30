package org.smileboy.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;

public class CovertUtil {

	@SuppressWarnings("unchecked")
	public static synchronized JSONObject classCovertJson(Object obj) throws Exception{
		JSONObject json = new JSONObject();
		Class<?> clazz=obj.getClass();//���ʵ������
		Field[] fields = obj.getClass().getDeclaredFields();//�������
		
		//���Object�����е����з���
		for(Field field:fields){
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
			Method getMethod = pd.getReadMethod();//���get����
			Object value = getMethod.invoke(obj);//�˴�Ϊִ�и�Object�����get����
			String key = getMethod.getName().substring(3,4).toLowerCase()+getMethod.getName().substring(4,getMethod.getName().length());
			if(value instanceof List<?>){
				json.put(key, listCovertJson((List<?>)value));
			}else if(value instanceof Vector<?>){
				json.put(key, vectorCovertJson((Vector<?>)value));
			}else if(value instanceof Map<?,?>){
				json.put(key, mapCovertJson((Map<Object,Object>)value));
			}else{
				json.put(key, value);
			}

		}
		return json;
	}
	
	public static JSONArray listCovertJson(List<?>list) throws Exception{
		JSONArray jsonArr = new JSONArray();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Object obj = list.get(i);
				jsonArr.put(classCovertJson(obj));
			}
		}
		
		return jsonArr;
	}
	
	public static JSONArray vectorCovertJson(Vector<?> vecotr) throws Exception{
		JSONArray jsonArr = new JSONArray();
		if(vecotr!=null&&vecotr.size()>0){
			for(int i=0;i<vecotr.size();i++){
				Object obj = vecotr.get(i);
				jsonArr.put(classCovertJson(obj));
			}
		}
		
		return jsonArr;
	}
	
	public static JSONArray mapCovertJson(Map<Object,Object> map) throws Exception{
		JSONArray jsonArr = new JSONArray();
		
		for(Map.Entry<Object,Object> entry : map.entrySet()) { 
			JSONObject json = new JSONObject();
			json.put(entry.getKey().toString(),classCovertJson(entry.getValue()));
			jsonArr.put(json);
		}

		return jsonArr;
	}
	
	
	public static synchronized Object paramCovertClass(HttpServletRequest request,Object obj) throws Exception{
		Class<?> cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();//�������
		
		
		//���Object�����е����з���
		for(Field field:fields){
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(),cls);
			Method setMethod = pd.getWriteMethod();//���set����	
			
			Enumeration<String> enu=request.getParameterNames();  
			while(enu.hasMoreElements()){
				String paraName=(String)enu.nextElement();  
				if(setMethod.getName().toLowerCase().equals("set"+paraName.toLowerCase())){
					//�˴�Ϊִ�и�Object�����set����
					Class<?> paramCls = setMethod.getParameterTypes()[0];
					String strValue = request.getParameter(paraName);
					if(paramCls.getName().equals("int")){
						setMethod.invoke(obj,Integer.parseInt(strValue));
					}else if(paramCls.getName().equals("long")){
						setMethod.invoke(obj,Long.parseLong(strValue));
					}else if(paramCls.getName().equals("double")){
						setMethod.invoke(obj,Double.parseDouble(strValue));
					}else if(paramCls.getName().equals("boolean")){
						setMethod.invoke(obj,Boolean.parseBoolean(strValue));
					}else if(paramCls.getName().equals("byte")){
						setMethod.invoke(obj,Byte.parseByte(strValue));
					}else{
						setMethod.invoke(obj,(Object)strValue);
					}
					
					break;
				}
			}
		}
		return obj;
	}
	

}
