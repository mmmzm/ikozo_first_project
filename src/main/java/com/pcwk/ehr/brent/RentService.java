package com.pcwk.ehr.brent;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.cmn.PLog;

public class RentService implements PLog {
	
	private RentDAO dao;
	
	public RentService() {
		dao = new RentDAO();
	}
	
	public int checkAndSave(RentDTO param) {
		int flag = dao.rentCheck(param);
		if(flag == 1) {
			log.debug("이미 대출된 책입니다.");
			return 0;
		}else {
			flag =dao.doSave(param);
			return flag;
		}
	}
	
	public int rentCheck(RentDTO param) {
		return dao.rentCheck(param);
	}
	
	public int rentStatus(RentDTO param) {
		
		int result = 0;
		int flag = dao.rentCheck(param);
		
		if(flag == 0) {
			result = 10; //대출 불가
			return result;
		}
		return 20;// 대출완료
	} //-- rentCheck end
	
	public DTO doRentSelect(RentDTO param) {
		int result = rentStatus(param);
		
		if(20 == result) {
			RentDTO outVO = dao.doSelectOne(param);
			return outVO;
		}else {
			MessageVO message = new MessageVO();
			message.setMessageId(String.valueOf(result));
			
			String messageStr = "";
			if(10 == result) {
				messageStr = "이미 대출된 도서입니다.";
			} else {
				messageStr = "...";
			}
			
			message.setMsgContents(messageStr);
			log.debug("message : {}", message);
			return message;
		}
	}
	
}
