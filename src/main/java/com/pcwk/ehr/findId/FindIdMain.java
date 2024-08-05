package com.pcwk.ehr.findId;

import com.pcwk.ehr.cmn.PLog;

public class FindIdMain implements PLog {
    private FindIdDAO dao;
    private FindIdDTO dto;

    public FindIdMain() {
        dao = new FindIdDAO();
        dto = new FindIdDTO();
    }

    public String findUserId(String userName, String userTel) {
        dto.setUserName(userName);
        dto.setUserTel(userTel);

        String userId = dao.findUserId(dto);

        if (userId != null) {
            log.debug("회원의 아이디는 " + userId + " 입니다.");
        } else {
            log.debug("해당 정보로 조회된 회원이 없습니다.");
        }

        return userId;
    }

    public static void main(String[] args) {
        FindIdMain main = new FindIdMain();

        String userName = "황혜원";
        String userTel = "010-3311-7177";

        main.findUserId(userName, userTel);
    }
}
