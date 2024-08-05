package com.pcwk.ehr.login;

import com.pcwk.ehr.cmn.DTO;

public class LoginDTO extends DTO {
    private String userId;
    private String userName;
    private String userTel;
    private String userPw;
    private String isAdmin; 
    private String userEmail;
    private String regId;
    private String regDt;
    private String modId;
    private String modDt;

    public LoginDTO() {
    }

    public LoginDTO(String userId, String userName, String userTel, String userPw, String isAdmin, String userEmail,
            String regId, String regDt, String modId, String modDt) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.userTel = userTel;
        this.userPw = userPw;
        this.isAdmin = isAdmin;
        this.userEmail = userEmail;
        this.regId = regId;
        this.regDt = regDt;
        this.modId = modId;
        this.modDt = modDt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getIsAdmin() { 
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) { 
        this.isAdmin = isAdmin;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    @Override
    public String toString() {
        return "LoginDTO [userId=" + userId + ", userName=" + userName + ", userTel=" + userTel + ", userPw=" + userPw
                + ", isAdmin=" + isAdmin + ", userEmail=" + userEmail + ", regId=" + regId + ", regDt=" + regDt
                + ", modId=" + modId + ", modDt=" + modDt + "]";
    }
}
