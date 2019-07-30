package org.smileboy.framework.bus;

import java.util.ArrayList;
import java.util.List;
import org.smileboy.framework.controller.Controller;
import org.smileboy.framework.controller.operation.ControllerOperation;

public class ProcessingBus {
	private static ProcessingBus instance = null;
	
	public static ProcessingBus getInstance(){
		if(instance==null){
			instance = new ProcessingBus();
		}
		return instance;
	}
	
	public void scanAnnotation(){
		List<String> packList = new ArrayList<String>();
		packList.add("test.action");
		
		List<Class<?>> classList = scanClass(packList);
		if(classList!=null&&classList.size()>0){
			annonAnalysis(classList);
		}
	}
	
	
	public List<Class<?>> scanClass(List<String> scanPathList){
		List<Class<?>> classList = new ArrayList<Class<?>>();
		if(scanPathList!=null&&scanPathList.size()>0){
			for(String path:scanPathList){
				ClassScanner Scanner = new ClassScanner();
				Scanner.scanning(path, false);
				classList.addAll(Scanner.getClassesList());
			}
		}
		return classList;
	}
	
	private void annonAnalysis(List<Class<?>> classList){
		
		for(Class<?> cls:classList){
			if(cls.isAnnotationPresent(Controller.class)){
				ControllerOperation.getInstance().addAnnonsClass(cls);
			}
		}
	}
}
