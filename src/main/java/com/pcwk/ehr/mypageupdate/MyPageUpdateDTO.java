package com.pcwk.ehr.mypageupdate;

public class MyPageUpdateDTO {
    private String userId;    // 사용자 아이디
    private String userName;  // 수정할 사용자 이름
    private String userTel;   // 수정할 사용자 전화번호
    private String userPw;    // 수정할 사용자 비밀번호
    private String userEmail; // 수정할 사용자 이메일
    
    public MyPageUpdateDTO() {
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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String toString() {
		return "MyPageUpdateDTO [userId=" + userId + ", userName=" + userName + ", userTel=" + userTel + ", userPw="
				+ userPw + ", userEmail=" + userEmail + ", toString()=" + super.toString() + "]";
	}
    
    
    
}
