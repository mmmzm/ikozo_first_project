package com.pcwk.ehr.cmn;

public class DTO {
	
	private int flag; // DML작성 상태값
	private int num; // 글번호
	private int totalCnt; // 총 글수
	private int bottomCount; // 바닥글 수 : 20240617 추가
	
	public DTO() {
		bottomCount = 10;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}	
	
	public int getBottomCount() {
		return bottomCount;
	}

	public void setBottomCount(int bottomCount) {
		this.bottomCount = bottomCount;
	}

	@Override
	public String toString() {
		return "DTO [flag=" + flag + ", num=" + num + ", totalCnt=" + totalCnt + ", bottomCount=" + bottomCount
				+ ", toString()=" + super.toString() + "]";
	}	
	
} // class 끝
