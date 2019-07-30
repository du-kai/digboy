package org.smileboy.framework.controller.operation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smileboy.framework.controller.RequestTrack;

public class ControllerOperation {
	private static ControllerOperation instance = null;
	private Map<String,ActionMapping> actionMap;
	private String postfix = ".action";
	
	
	public static ControllerOperation getInstance(){
		if(instance == null){
			instance = new ControllerOperation();
		}
		
		return instance;
	}
	
	public void addAnnonsClass(Class<?> cls){
		boolean hasRequestTrack = cls.isAnnotationPresent(RequestTrack.class);

    	if(hasRequestTrack){
    		RequestTrack reuqestTrack = (RequestTrack)cls.getAnnotation(RequestTrack.class);
    		String rootPath = reuqestTrack.value();
    		String endStr = rootPath.substring( rootPath.length()-1,rootPath.length());
    		String startStr = rootPath.substring(0,1);
    		
    		rootPath = endStr.equals("/")?rootPath:rootPath+"/";
    		rootPath = startStr.equals("/")?rootPath:"/"+rootPath;
    		
    		for(Method method:cls.getMethods()){
    			 Annotation[] ans = method.getAnnotations();
    			 if(ans.length>0){
    				 for(int i=0;i<ans.length;i++){
    					 Annotation an = ans[i]; 
    					 if(an.annotationType().equals(RequestTrack.class)){
    						 String actionPath = ((RequestTrack)ans[i]).value();
    						 actionPath = actionPath.substring(0,1).equals("/")?actionPath.substring(1,actionPath.length()):actionPath;
    						 addMap(rootPath+actionPath,cls,method);
    						 break;
    					 }
    				 }
    			 }
    		}
    	}
	}
	
	public void excuteRequest(String path,HttpServletRequest request,HttpServletResponse response) throws Exception{
		if(actionMap!=null&&actionMap.containsKey(path)){
			ActionMapping actionMapping = actionMap.get(path);
			MethodExcuse.getInstance().excuteMethod(actionMapping, request, response);
		}
	}
	
	private void addMap(String path,Class<?> cls,Method method){
		if(actionMap==null){
			actionMap = new HashMap<String, ActionMapping>();
		}
		
		String classPath = cls.getName();
		String methodName = method.getName();
		ActionMapping actionMapping = new ActionMapping();
		actionMapping.setClassPath(classPath);
		actionMapping.setMethod(methodName);
		actionMap.put(path, actionMapping);
		System.out.println("path =    "+path+"    classPath ="+classPath+"   method = "+methodName);
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}
	
	
}
