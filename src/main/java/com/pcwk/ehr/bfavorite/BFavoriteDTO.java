package com.pcwk.ehr.bfavorite;

import com.pcwk.ehr.cmn.DTO;

public class BFavoriteDTO extends DTO {
	private int    favSeq;  //시퀀스
	private String userId;  //회원아이디
	private int    bookCode;//도서번호
	private String regDt;   //등록일
	
	public BFavoriteDTO() {
		
	}

	public BFavoriteDTO(int favSeq, String userId, int bookCode, String regDt) {
		super();
		this.favSeq = favSeq;
		this.userId = userId;
		this.bookCode = bookCode;
		this.regDt = regDt;
	}

	public int getFavSeq() {
		return favSeq;
	}

	public void setFavSeq(int favSeq) {
		this.favSeq = favSeq;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getBookCode() {
		return bookCode;
	}

	public void setBookCode(int bookCode) {
		this.bookCode = bookCode;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	@Override
	public String toString() {
		return "B_FavoriteDTO [favSeq=" + favSeq + ", userId=" + userId + ", bookCode=" + bookCode + ", regDt=" + regDt
				+ ", toString()=" + super.toString() + "]";
	}
	
	

}
