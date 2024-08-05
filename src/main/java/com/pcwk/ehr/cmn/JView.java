package com.pcwk.ehr.cmn;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Controller에서 생성된 JSP View경로를 전달
 * @author acron
 * */
public class JView {
	// jsp view이름
	private String viewName;

	public JView(String viewName) {
		super();
		this.viewName = viewName;
	}

	/*
	 * viewName getter()
	 * @return
	 * */
	public String getViewName() {
		return viewName;
	}
	
	
	/*
	 * viewName으로 request, response 전달
	 * @param request
	 * @param response
	 * */
	public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispacher = request.getRequestDispatcher(viewName);
    	dispacher.forward(request, response);
	}
}
