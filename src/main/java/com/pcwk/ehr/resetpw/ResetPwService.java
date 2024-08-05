package com.pcwk.ehr.resetpw;

public class ResetPwService {
    private ResetPwDAO dao;
    private String newPassword; // 비밀번호 재설정 후 새로운 비밀번호를 저장하기 위한 변수

    public ResetPwService() {
        dao = new ResetPwDAO();
    }

    public boolean resetPassword(String userId, String userName, String userTel) {
        ResetPwDTO dto = new ResetPwDTO();
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setUserTel(userTel);

        boolean isResetSuccessful = dao.resetPassword(dto);
        if (isResetSuccessful) {
            this.newPassword = dto.getNewPassword(); // 새로운 비밀번호 저장
        }

        return isResetSuccessful;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
