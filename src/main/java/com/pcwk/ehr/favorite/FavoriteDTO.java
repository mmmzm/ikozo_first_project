package com.pcwk.ehr.favorite;

import com.pcwk.ehr.cmn.DTO;

public class FavoriteDTO extends DTO {
    private int favSeq; // 즐겨찾기 시퀀스
    private String userId; // 회원 아이디
    private int bookCode; // 도서 번호
    private String regDt; // 등록 일자
    private String bookName; // 도서 제목
    private String bookGenre; // 도서 장르
    private String author; // 작가

    public FavoriteDTO() {
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "FavoriteDTO [favSeq=" + favSeq + ", userId=" + userId + ", bookCode=" + bookCode + ", regDt=" + regDt
                + ", bookName=" + bookName + ", bookGenre=" + bookGenre + ", author=" + author + "]";
    }
}
