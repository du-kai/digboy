package org.smileboy.framework.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.smileboy.framework.bus.ProcessingBus;
import org.smileboy.framework.controller.operation.ControllerOperation;

@SuppressWarnings("serial")
public class BusDispatcherServlet extends HttpServlet {
	@Override
	public void init(final ServletConfig config) throws ServletException {
		ProcessingBus.getInstance().scanAnnotation();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		process(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req,resp);
	}
	
	private void process(HttpServletRequest req, HttpServletResponse resp){
		
		String address = req.getRequestURI().replace(req.getContextPath(), "");
		String[] addressArr = address.split("\\.");
		if(addressArr.length>0){
			int doLength = addressArr[addressArr.length-1].length();
			doLength+=1;
			address = address.substring(0,address.length()-doLength);
		}
		try{
			ControllerOperation.getInstance().excuteRequest(address,req,resp);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
}
