package com.pcwk.ehr.brent;

import com.pcwk.ehr.cmn.PLog;

public class RentMain implements PLog {

	RentService service;
	RentDTO rent;
	
	public RentMain() {
		service = new RentService();
		rent = new RentDTO();
		rent.setRentCode(254);
		
	}
	
	public void doSave() {
		log.debug("do save()");
		int flag = service.checkAndSave(rent);
		if(1 == flag) {
			log.debug("성공 : {}", flag);
		}else 
			log.debug("실패 : {} ", flag);
	}
	
	public void rentCheck() {
		log.debug("rentCheck()");
		int flag = service.rentCheck(rent);
		if(1 == flag) {
			log.debug("rentCheck 성공 : {}", flag);
		}else {
			log.debug("rentCheck 실패 : {}", flag);
		}
		
	}
	
	public static void main(String[] args) {
		RentMain r = new RentMain();
		//r.doSave();
		//r.rentCheck();
	}

}
