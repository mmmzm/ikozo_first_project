package com.pcwk.ehr.cmn;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class StringUtil implements PLog{
	/*
	 * 페이지 네이션
	 * @param maxNum : 총글수
	 * @param currentpageNo : 페이지 번호
	 * @param rowPerPage : 페이지 사이즈 > 10/20/30/50/100/200
	 * @param bottomCount : 10/5
	 * @param url : 서버호출 URL
	 * @param scriptName : 자바스크립트 명
	 * @param paging Html
	 * */
	public static String renderingPaging(int maxNum, int currentPageNo, int rowPerPage, int bottomCount, String url, String scriptName) {
		StringBuilder html = new StringBuilder(2000);
		
		// 전체 모양
		// << < 1 2 3 .. 10 > >>
		//----------------------------------------------------------------------
		
		//maxNum = 21
		//currentPageNo = 1
		//rowPerPage = 10
		//bottomCount = 10
		
		int maxPageNO 	= (maxNum - 1)/rowPerPage + 1;
		int startPageNo = ((currentPageNo - 1)/bottomCount) * bottomCount + 1;//1,11,21 ...
		int endPageNo 	= ((currentPageNo - 1)/bottomCount+1) * bottomCount;//10,20,30 ...
		
		int nowBlockNo = ((currentPageNo - 1)/bottomCount)+1; // 1
		int maxBlockNo = ((maxNum - 1)/bottomCount) + 1; // 3
		
		//----------------------------------------------------------------------
		
		if(currentPageNo > maxPageNO) {
			return "";
		}
		
		html.append("<ul class=\"pagination justify-content-center\"> \n");
		//<<
		
		if(nowBlockNo > 1 && nowBlockNo <= maxBlockNo) {
			log.debug("nowBlocNo:"+nowBlockNo);
			log.debug("maxBlocNo:"+maxBlockNo);
			
			html.append("<li class=\"page-item\"> \n"); 
			                                                      //scriptName(url,1);
			html.append("<a class=\"page-link\" href=\"javascript:"+scriptName+"('"+url+ "',1);\">\n");
			html.append("<span aria-hidden=\"true\">&laquo;</span> \n");
			html.append("</a> \n");
			
			html.append("</li> \n");
		}
		
		//<
		if(startPageNo > bottomCount) {
			log.debug("startPageNo:"+startPageNo);
			log.debug("bottomCount:"+bottomCount);
			
			html.append(" <li class=\"page-item\"> \n");
			html.append("<a class=\"page-link\" href=\"javascript:"+scriptName+"('"+url+ "',"+(startPageNo - bottomCount) +");\" >\n");
			html.append(" <span aria-hidden=\"true\">&lt;</span> \n");
			
			html.append("</a> \n");
			html.append("</li> \n");
		}
		
		//1  2  3 ... 10
		int inx = 0;
		for(inx = startPageNo; inx <= maxPageNO && inx <= endPageNo; inx++) {
			
		  if(inx == currentPageNo) {
			html.append("<li class=\"page-item\"> \n");
			html.append("<a class=\"page-link active\" href=\"#\">");
			html.append(inx);
			html.append("</a> \n");
			html.append("</li> \n");
		  }else {
			html.append("<li class=\"page-item\"> \n");
			html.append("<a class=\"page-link\" href=\"javascript:"+scriptName+"('"+ url + "',"+ inx +");\" >");
		    html.append(inx);
			html.append("</a> \n");
			html.append("</li> \n");
			}
		}
		
		//>
		if(maxPageNO > inx) {
			log.debug("nowBlocNo:"+nowBlockNo);
			log.debug("bottomCount:"+bottomCount);
			log.debug("bottomCount:"+((nowBlockNo * bottomCount)+1));
			html.append(" <li class=\"page-item\"> \n");
			html.append("<a class=\"page-link\" href=\"javascript:"+scriptName+"('"+url+ "',"+((nowBlockNo * bottomCount)+1) +");\" >\n");
			html.append(" <span aria-hidden=\"true\">&gt;</span> \n");
			html.append("</a> \n");
			
			html.append("</li> \n");
		}
		
		//>>
		if(maxPageNO > inx) {
		  html.append("<li class=\"page-item\"> \n");
		  html.append("<a class=\"page-link\" href=\"javascript:"+scriptName+"('"+url+ "',"+(maxPageNO) +");\" > \n");
		  html.append("<span aria-hidden=\"true\">&raquo;</span>\n");
		  html.append("</a> \n");
		  html.append("</li> \n");
		}
		
		html.append("</ul> \n");
		
		return html.toString();
	}	
	
	public static String nvl(String value, String defaultValue) {
		  if(null == value || value.trim().isEmpty()){
			  System.out.println("value : " + value);
			  return defaultValue;
		  }		
		return value;
	}
	/*
	 * 32bit UUID 생성
	 * @return String
	 * */
	public static String getUUID() {
		// String resultUUID = "";
		UUID uuidTemp = UUID.randomUUID();
		
		// System.out.println(uuidTemp);
		// System.out.println(uuidTemp.toString().replaceAll("-", "").length());
		
		return uuidTemp.toString().replaceAll("-", "");
	} // String
} // class
