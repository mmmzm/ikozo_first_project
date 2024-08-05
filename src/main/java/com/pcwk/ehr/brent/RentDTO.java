package com.pcwk.ehr.brent;
import com.pcwk.ehr.cmn.DTO;

public class RentDTO extends DTO {
	private int rentCode; // 대출번호
	private String userId; // 회원아이디
	private int bookCode; // 도서번호
	private String rentDate; // 대출날짜
	private String dueDate; // 반납예정일
	private String returnedDate; // 반납일
	private String noreturnCount; // 반납여부
	private int extraSum; // 연체금액
	private String regDt; // 등록일
	private String regId; // 등록자
	private String modId; // 수정자
	private String modDt; // 수정일

	public RentDTO() {

	}

	public RentDTO(int rentCode, String userId, int bookCode, String rentDate, String dueDate, String returnedDate,
			String noreturnCount, int extraSum, String regDt, String regId, String modId, String modDt) {
		super();
		this.rentCode = rentCode;
		this.userId = userId;
		this.bookCode = bookCode;
		this.rentDate = rentDate;
		this.dueDate = dueDate;
		this.returnedDate = returnedDate;
		this.noreturnCount = noreturnCount;
		this.extraSum = extraSum;
		this.regDt = regDt;
		this.regId = regId;
		this.modId = modId;
		this.modDt = modDt;
	}

	public int getRentCode() {
		return rentCode;
	}

	public void setRentCode(int rentCode) {
		this.rentCode = rentCode;
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

	public String getRentDate() {
		return rentDate;
	}

	public void setRentDate(String rentDate) {
		this.rentDate = rentDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate(String returnedDate) {
		this.returnedDate = returnedDate;
	}

	public String getNoreturnCount() {
		return noreturnCount;
	}

	public void setNoreturnCount(String noreturnCount) {
		this.noreturnCount = noreturnCount;
	}

	public int getExtraSum() {
		return extraSum;
	}

	public void setExtraSum(int extraSum) {
		this.extraSum = extraSum;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
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
		return "RentDTO [rentCode=" + rentCode + ", userId=" + userId + ", bookCode=" + bookCode + ", rentDate="
				+ rentDate + ", dueDate=" + dueDate + ", returnedDate=" + returnedDate + ", noreturnCount="
				+ noreturnCount + ", extraSum=" + extraSum + ", regDt=" + regDt + ", regId=" + regId + ", modId="
				+ modId + ", modDt=" + modDt + ", toString()=" + super.toString() + "]";
	}

}
