package org.smileboy.framework.controller.operation;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.smileboy.framework.controller.viewobject.ViewModel;
import org.smileboy.util.CovertUtil;
import org.smileboy.util.TypeCheckingUtil;

public class MethodExcuse {
	private static MethodExcuse instance = null;
	
	public static MethodExcuse getInstance(){
		if(instance == null){
			instance = new MethodExcuse();
		}
		return instance;
	}
	
	public void excuteMethod(ActionMapping actionMapping,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Class<?> cls = Class.forName(actionMapping.getClassPath());
		Object newInstance = cls.newInstance();
		Method[] methods =  newInstance.getClass().getMethods();
		Method method = null;
		
		// 获取方法
		for(Method met:methods){
			if(met.getName().equals(actionMapping.getMethod())){
				method = met;
				break;
			}
		}

		if(method == null){
			return;
		}
		
		// 执行
		Class<?>[] paramTypes = method.getParameterTypes();
		Object[] param = new Object[paramTypes.length];
		if(paramTypes.length>0){
			for(int i=0;i<paramTypes.length;i++){
				Class<?> paramClass = paramTypes[i];
				if(paramClass == HttpServletRequest.class){
					param[i] = request;
				}else if(paramClass == HttpServletResponse.class){
					param[i] = response;
				}else{
					param[i] = CovertUtil.paramCovertClass(request,((Class<?>)paramClass).newInstance());
				}
			}
		}
		Object returnData = method.invoke(newInstance, param);
		retunProcess(returnData,request,response);
	}
	
	private void retunProcess(Object returnData,HttpServletRequest request,HttpServletResponse response){
		if(returnData!=null){
			
			// 构建response头信息
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setLocale(new Locale("zh","CN"));
			
			if(returnData instanceof ViewModel){ // 给页面设置参数
				Map<String, Object> map = ((ViewModel)returnData).getAttribute();
				
				for(Map.Entry<String, Object> entry : map.entrySet()) { 
				 	request.setAttribute(entry.getKey(),entry.getValue());
				}
				ViewModel viewModel = (ViewModel)returnData;
				try{
					request.getRequestDispatcher(viewModel.getPageAndPath()).forward(request, response);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}else if(!TypeCheckingUtil.isBasic(returnData)){
				try{
					JSONObject json = CovertUtil.classCovertJson(returnData);
					response.getWriter().println(json);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		}
		
	}
}
