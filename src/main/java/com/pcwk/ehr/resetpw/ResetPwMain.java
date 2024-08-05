package com.pcwk.ehr.resetpw;

import com.pcwk.ehr.cmn.PLog;

public class ResetPwMain implements PLog {

    private ResetPwDAO dao;

    public ResetPwMain() {
        dao = new ResetPwDAO();
    }

    public void testResetPassword() {
        // 테스트 데이터 설정
        String testUserId = "admin4";
        String testUserName = "황혜원";
        String testUserTel = "010-3311-7177";

        // 테스트 데이터
        ResetPwDTO dto = new ResetPwDTO();
        dto.setUserId(testUserId);
        dto.setUserName(testUserName);
        dto.setUserTel(testUserTel);

        // 비밀번호 초기화
        boolean isResetSuccessful = dao.resetPassword(dto);

       
        if (isResetSuccessful) {
            log.debug("비밀번호 초기화 성공! 새로운 비밀번호: " + dto.getNewPassword());
        } else {
            log.debug("비밀번호 초기화 실패! 사용자 정보를 확인해주세요.");
        }
    }

    public static void main(String[] args) {
        ResetPwMain m = new ResetPwMain();
        m.testResetPassword();
    }
}
