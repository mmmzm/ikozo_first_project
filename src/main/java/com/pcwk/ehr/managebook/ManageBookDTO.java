package com.pcwk.ehr.managebook;

import com.pcwk.ehr.cmn.DTO;

public class ManageBookDTO extends DTO {
	private int    rnum; // 번호
	private int	   bookCode; // 도서번호
	private String bookName; // 도서제목
	private int	   bookGenre; // 장르코드
	private String genre; // 장르
	private String author; // 작가
	private String publisher; // 출판사
	private String rentYn; // 대출가능 여부
	private String isbn; // isbn
	private String bookPubDate; // 출판일
	private String bookInfo; // 도서 소개
	private String rentDate; // 대출일
	private String dueDate; // 반납예정일
	private String retunredDate; // 반납일
	private String noreturnCount; // 대출연장카운트
	private int	   rentCode; // 대출코드
	private String regId; // 등록자
	private String regDt; // 등록일
	private String modId; // 수정자
	private String modDt; // 수정일
	
	public ManageBookDTO() {} // 빈통

	public ManageBookDTO(int rnum, int bookCode, String bookName, int bookGenre, String genre, String author,
			String publisher, String rentYn, String isbn, String bookPubDate, String bookInfo, String rentDate,
			String dueDate, String retunredDate, String noreturnCount, int rentCode, String regId, String regDt,
			String modId, String modDt) {
		super();
		this.rnum = rnum;
		this.bookCode = bookCode;
		this.bookName = bookName;
		this.bookGenre = bookGenre;
		this.genre = genre;
		this.author = author;
		this.publisher = publisher;
		this.rentYn = rentYn;
		this.isbn = isbn;
		this.bookPubDate = bookPubDate;
		this.bookInfo = bookInfo;
		this.rentDate = rentDate;
		this.dueDate = dueDate;
		this.retunredDate = retunredDate;
		this.noreturnCount = noreturnCount;
		this.rentCode = rentCode;
		this.regId = regId;
		this.regDt = regDt;
		this.modId = modId;
		this.modDt = modDt;
	}

	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	public int getBookCode() {
		return bookCode;
	}

	public void setBookCode(int bookCode) {
		this.bookCode = bookCode;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getBookGenre() {
		return bookGenre;
	}

	public void setBookGenre(int bookGenre) {
		this.bookGenre = bookGenre;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getRentYn() {
		return rentYn;
	}

	public void setRentYn(String rentYn) {
		this.rentYn = rentYn;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookPubDate() {
		return bookPubDate;
	}

	public void setBookPubDate(String bookPubDate) {
		this.bookPubDate = bookPubDate;
	}

	public String getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(String bookInfo) {
		this.bookInfo = bookInfo;
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

	public String getRetunredDate() {
		return retunredDate;
	}

	public void setRetunredDate(String retunredDate) {
		this.retunredDate = retunredDate;
	}

	public String getNoreturnCount() {
		return noreturnCount;
	}

	public void setNoreturnCount(String noreturnCount) {
		this.noreturnCount = noreturnCount;
	}

	public int getRentCode() {
		return rentCode;
	}

	public void setRentCode(int rentCode) {
		this.rentCode = rentCode;
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
		return "ManageBookDTO [rnum=" + rnum + ", bookCode=" + bookCode + ", bookName=" + bookName + ", bookGenre="
				+ bookGenre + ", genre=" + genre + ", author=" + author + ", publisher=" + publisher + ", rentYn="
				+ rentYn + ", isbn=" + isbn + ", bookPubDate=" + bookPubDate + ", bookInfo=" + bookInfo + ", rentDate="
				+ rentDate + ", dueDate=" + dueDate + ", retunredDate=" + retunredDate + ", noreturnCount="
				+ noreturnCount + ", rentCode=" + rentCode + ", regId=" + regId + ", regDt=" + regDt + ", modId="
				+ modId + ", modDt=" + modDt + ", toString()=" + super.toString() + "]";
	}
	
} // class