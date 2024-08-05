package com.pcwk.ehr.bfavorite;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.ControllerV;
import com.pcwk.ehr.cmn.JView;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.StringUtil;

public class BFavoriteController extends HttpServlet implements ControllerV, PLog {
	private static final long serialVersionUID = 1L;
	
	BFavoriteService service;

	public JView doFavSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
		log.debug("doSave()");
		log.debug("---------------------");
		
		BFavoriteDTO inVO = new BFavoriteDTO();
		
		String userId = StringUtil.nvl(request.getParameter("userId"),"");
		int bookCode = Integer.parseInt(StringUtil.nvl(request.getParameter("book_code"),"0"));
		
		log.debug("userId:{}", userId);
		
		inVO.setUserId(userId);
		inVO.setBookCode(bookCode);
		
		int flag = this.service.doFavSave(inVO);
		log.debug("flag:{}", flag);
		
		String message = "";
		if(flag == 1) {
			message = "즐겨찾기 완료";
		}else {
			message = "등록 실패";
		}
		
		MessageVO messageVO = new MessageVO();
		messageVO.setMessageId(String.valueOf(flag));
		messageVO.setMsgContents(message);
		
		log.debug("messageVO:{}",messageVO);
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(messageVO);
		
		log.debug("jsonStirng:{}", jsonString);
		
		//한글 깨짐 설정
		response.setContentType("text/html; charset=UTF-8");
		
		//sussess data 화면에 출력
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		
		return null;
	}	
		
	@Override
	public JView doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("---------------------");
		log.debug("doWork()");
		log.debug("---------------------");
		
		JView viewName = null;
		
		String workDiv = StringUtil.nvl(request.getParameter("work_div"),"");
		
		switch(workDiv) {
		case "doSave":
			viewName = doFavSave(request,response);
			break;
		default:
			log.debug("작업구분을 확인 하세요. workDiv : {}", workDiv);
			break;
		}
		
		return viewName;
	}
	
}
