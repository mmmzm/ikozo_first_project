package com.pcwk.ehr.cmn;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 모든 Controller는 ControllerV를 implements 받아야 한다
 * @author acorn
 * */
public interface ControllerV{
	
	/*
	 * Controller 공통 메서드
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * */
	public JView doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
