package com.pcwk.ehr.resetpw;

public class ResetPwDTO {
    private String userId;
    private String userName;
    private String userTel;
    private String newPassword;

	
	public ResetPwDTO() {
    }


	public ResetPwDTO(String userId, String userName, String userTel, String newPassword) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userTel = userTel;
		this.newPassword = newPassword;
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


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


	@Override
	public String toString() {
		return "ResetPwDTO [userId=" + userId + ", userName=" + userName + ", userTel=" + userTel + ", newPassword="
				+ newPassword + ", toString()=" + super.toString() + "]";
	}

	
	
	
	
	
	
	
	
}
