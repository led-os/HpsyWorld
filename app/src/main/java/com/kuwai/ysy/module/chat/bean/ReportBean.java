package com.kuwai.ysy.module.chat.bean;

public class ReportBean {

    private String reason;
    private boolean isCheck = false;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public ReportBean(String reason, boolean isCheck) {
        this.reason = reason;
        this.isCheck = isCheck;
    }

    public ReportBean(String reason, boolean isCheck, int id) {
        this.reason = reason;
        this.isCheck = isCheck;
        this.id = id;
    }
}
