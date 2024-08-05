package com.pcwk.ehr.mypageupdate;

import com.pcwk.ehr.cmn.PLog;

public class MyPageUpdateMain implements PLog {

    private MyPageUpdateService service;

    public MyPageUpdateMain() {
        service = new MyPageUpdateService();
    }

    public void testUpdateUser() {
        // 테스트 데이터 설정
        MyPageUpdateDTO dto = new MyPageUpdateDTO();
        dto.setUserId("testuser1");
        dto.setUserName("수정된 이름");
        dto.setUserTel("010-1234-5678");
        dto.setUserPw("newpassword");
        dto.setUserEmail("updated@example.com");

        // 사용자 정보 업데이트 시도
        boolean isSuccess = service.updateUser(null, dto);

        // 결과 출력
        if (isSuccess) {
            log.debug("사용자 정보 업데이트 성공!");
        } else {
            log.debug("사용자 정보 업데이트 실패!");
        }
    }

    public static void main(String[] args) {
        MyPageUpdateMain m = new MyPageUpdateMain();
        m.testUpdateUser();
    }
}
