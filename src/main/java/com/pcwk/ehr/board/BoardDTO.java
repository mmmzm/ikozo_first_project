package com.pcwk.ehr.board;

import com.pcwk.ehr.cmn.DTO;
import java.sql.Date;

public class BoardDTO extends DTO {
    private int seq;           // 순번 (기본키)
    private String title;      // 제목
    private int readCnt;       // 조회수
    private String contents;   // 내용
    private String regId;      // 등록자
    private Date regDt;        // 등록일
    private String modId;      // 수정자
    private Date modDt;        // 수정일
    private String isAdmin;    // 관리자 여부 (Y/N)

    // 기본 생성자
    public BoardDTO() {}

    // 매개변수 있는 생성자
    public BoardDTO(int seq, String title, int readCnt, String contents, String regId, Date regDt, String modId, Date modDt, String isAdmin) {
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

    // Getter와 Setter 메서드
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

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public Date getModDt() {
        return modDt;
    }

    public void setModDt(Date modDt) {
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
        return "BoardDTO [seq=" + seq + ", title=" + title + ", readCnt=" + readCnt + ", contents=" + contents
                + ", regId=" + regId + ", regDt=" + regDt + ", modId=" + modId + ", modDt=" + modDt + ", isAdmin="
                + isAdmin + ", toString()=" + super.toString() + "]";
    }
}
