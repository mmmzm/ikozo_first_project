package com.pcwk.ehr.mypageupdate;

import javax.servlet.http.HttpSession;

import com.pcwk.ehr.cmn.PLog;

public class MyPageUpdateService implements PLog {
    private MyPageUpdateDAO dao;

    public MyPageUpdateService() {
        dao = new MyPageUpdateDAO();
    }

    /**
     * 로그인된 사용자의 회원 정보 업데이트 메서드
     * 
     * @param session HttpSession 객체를 통해 로그인된 사용자 정보를 가져옴
     * @param dto     업데이트할 사용자 정보를 담고 있는 MyPageUpdateDTO 객체
     * @return boolean 업데이트 성공 여부 (true: 성공, false: 실패)
     */
    public boolean updateUser(HttpSession session, MyPageUpdateDTO dto) {
        boolean isSuccess = false;

        try {
            // 세션에서 로그인된 사용자 아이디 가져오기
            String loggedInUserId = (String) session.getAttribute("loggedInUserId");
            if (loggedInUserId == null) {
                log.error("세션에 로그인된 사용자 정보가 없습니다.");
                return false;
            }

            // 사용자 아이디 설정
            dto.setUserId(loggedInUserId);

            // 정보 업데이트 수행
			/* isSuccess = dao.updateUser(dto); */
            if (isSuccess) {
                log.debug("사용자 정보가 업데이트 되었습니다.");
            } else {
                log.debug("사용자 정보 업데이트에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("사용자 정보 업데이트 중 오류 발생: " + e.getMessage());
        }

        return isSuccess;
    }
}

