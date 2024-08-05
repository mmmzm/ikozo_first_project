package com.pcwk.ehr.answer;

import java.util.Date;

public class AnswerDTO {
    private int seq;
    private int boardSeq;
    private String contents;
    private String regId;
    private Date regDt;
    private String modId;
    private Date modDt;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getBoardSeq() {
        return boardSeq;
    }

    public void setBoardSeq(int boardSeq) {
        this.boardSeq = boardSeq;
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

    @Override
    public String toString() {
        return "AnswerDTO [seq=" + seq + ", boardSeq=" + boardSeq + ", contents=" + contents + ", regId=" + regId
                + ", regDt=" + regDt + ", modId=" + modId + ", modDt=" + modDt + "]";
    }
}
