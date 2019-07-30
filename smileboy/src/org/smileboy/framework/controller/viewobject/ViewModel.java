package org.smileboy.framework.controller.viewobject;

import java.util.HashMap;
import java.util.Map;

public class ViewModel {
	private String pageAndPath;
	private Map<String,Object> attribute;
	
	public ViewModel(String page) {
		pageAndPath = page;
	}
	
	public void addAttribute(String key,Object value){
		if(attribute == null){
			attribute = new HashMap<String, Object>();
		}
		
		attribute.put(key, value);
	}
	
	public void removeAttribute(String key){
		if(attribute != null){
			attribute.remove(key);
		}
	}
	
	public Map<String,Object> getAttribute(){
		if(attribute == null){
			attribute = new HashMap<String, Object>();
		}
		
		return attribute;
	}

	public String getPageAndPath() {
		return pageAndPath;
	}

	
	
}
