package com.pcwk.ehr.booklist;

import com.pcwk.ehr.cmn.DTO;

public class BookDTO extends DTO {
	private int bookCode;// 시퀀스
	private int bookGenre;// 장르코드
	private String genreName; // 장르코드
	private String bookName;// 도서제목
	private long isbn;// ISBN
	private String bookPubDate;// 발간날짜
	private String publisher;// 출판사
	private String author;// 작가
	private String bookInfo;// 도서소개
	private String regId;// 등록자
	private String regDt;// 등록날짜
	private String modId;// 수정자
	private String modDt;// 수정날짜

	public BookDTO() {
	}

	public BookDTO(int bookCode, int bookGenre, String bookName, long isbn, String bookPubDate, String publisher,
			String author, String bookInfo, String regId, String regDt, String modId, String modDt) {
		super();
		this.bookCode = bookCode;
		this.bookGenre = bookGenre;
		this.bookName = bookName;
		this.isbn = isbn;
		this.bookPubDate = bookPubDate;
		this.publisher = publisher;
		this.author = author;
		this.bookInfo = bookInfo;
		this.regId = regId;
		this.regDt = regDt;
		this.modId = modId;
		this.modDt = modDt;

	}

	public BookDTO(int bookCode, int bookGenre, String genreName, String bookName, long isbn, String bookPubDate,
			String publisher, String author, String bookInfo, String regId, String regDt, String modId, String modDt,
			int seq) {
		super();
		this.bookCode = bookCode;
		this.bookGenre = bookGenre;
		this.genreName = genreName;
		this.bookName = bookName;
		this.isbn = isbn;
		this.bookPubDate = bookPubDate;
		this.publisher = publisher;
		this.author = author;
		this.bookInfo = bookInfo;
		this.regId = regId;
		this.regDt = regDt;
		this.modId = modId;
		this.modDt = modDt;
		this.seq = seq;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public int getBookCode() {
		return bookCode;
	}

	public void setBookCode(int bookCode) {
		this.bookCode = bookCode;
	}

	public int getBookGenre() {
		return bookGenre;
	}

	public void setBookGenre(int bookGenre) {
		this.bookGenre = bookGenre;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	public String getBookPubDate() {
		return bookPubDate;
	}

	public void setBookPubDate(String bookPubDate) {
		this.bookPubDate = bookPubDate;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(String bookInfo) {
		this.bookInfo = bookInfo;
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
		return "BookDTO [bookCode=" + bookCode + ", bookGenre=" + bookGenre + ", genreName=" + genreName + ", bookName="
				+ bookName + ", isbn=" + isbn + ", bookPubDate=" + bookPubDate + ", publisher=" + publisher
				+ ", author=" + author + ", bookInfo=" + bookInfo + ", regId=" + regId + ", regDt=" + regDt + ", modId="
				+ modId + ", modDt=" + modDt + ", seq=" + seq + ", toString()=" + super.toString() + "]";
	}

	private int seq;

	// 기존의 다른 코드들...
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
}