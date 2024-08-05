package com.pcwk.ehr.findId;

public class FindIdDTO {
    private String userName; // 회원 이름
    private String userTel; // 회원 전화번호

    // 생성자
    public FindIdDTO() {
    }

    
    
	public FindIdDTO(String userName, String userTel) {
		super();
		this.userName = userName;
		this.userTel = userTel;
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

	@Override
	public String toString() {
		return "FindIdDTO [userName=" + userName + ", userTel=" + userTel + ", toString()=" + super.toString() + "]";
	}




    
    
}

  