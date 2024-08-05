package com.pcwk.ehr.mainpage;

import com.pcwk.ehr.cmn.DTO;

public class MainPageDTO extends DTO{
	private int    seq       ;//순번
	private String title     ;//제목
	private int    readCnt  ;//조회수
	private String contents  ;//내용
	private String regId    ;//등록자
	private String regDt    ;//등록일
	private String modId    ;//수정자
	private String modDt    ;//수정일
	private String isAdmin  ;//관리자여부(Y:관리자/N:회원)
	
	
	public MainPageDTO() {}


	public MainPageDTO(int seq, String title, int readCnt, String contents, String regId, String regDt, String modId,
			String modDt, String isAdmin) {
		super();
		this.seq = seq;
		this.title = title;
		this.readCnt = readCnt;
		this.contents = contents;
		this.regId = regId;
		this.regDt = regDt;
		this.modId = modId;
		this.modDt = modDt;
		this.isAdmin = isAdmin;
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getReadCnt() {
		return readCnt;
	}


	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}


	public String getContents() {
		return contents;
	}


	public void setContents(String contents) {
		this.contents = contents;
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


	public String getIsAdmin() {
		return isAdmin;
	}


	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}


	@Override
	public String toString() {
		return "MainPageDTO [seq=" + seq + ", title=" + title + ", readCnt=" + readCnt + ", contents=" + contents
				+ ", regId=" + regId + ", regDt=" + regDt + ", modId=" + modId + ", modDt=" + modDt + ", isAdmin="
				+ isAdmin + ", toString()=" + super.toString() + "]";
	}


	

}
