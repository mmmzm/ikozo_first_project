package com.pcwk.ehr.join;

import com.pcwk.ehr.cmn.DTO;

public class JoinDTO extends DTO{
	
	private String userId     ;//회원아이디
	private String userName   ;//회원이름
	private String userTel    ;//전화번호
	private String userPw     ;//회원비번
	private String isAdmin    ;//관리자 Y/N 기본값: N
	private String userEmail  ;//이메일
	private String regId      ;//등록자
	private String regDt      ;//등록일
	private String modId      ;//수정자
	private String modDt      ;//수정일

	public JoinDTO() {}

	public JoinDTO(String userId, String userName, String userTel, String userPw, String isAdmin, String userEmail,
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
		return "JoinDTO [userId=" + userId + ", userName=" + userName + ", userTel=" + userTel + ", userPw=" + userPw
				+ ", isAdmin=" + isAdmin + ", userEmail=" + userEmail + ", regId=" + regId + ", regDt=" + regDt
				+ ", modId=" + modId + ", modDt=" + modDt + ", toString()=" + super.toString() + "]";
	}
	
	
	
	
	
}


