package com.pcwk.ehr.findId;

public class FindIdService {

    private FindIdDAO dao;

    public FindIdService() {
        dao = new FindIdDAO();
    }

    public String findUserId(String userName, String userTel) {
        FindIdDTO dto = new FindIdDTO(userName, userTel);
        return dao.findUserId(dto);
    }
    
}
