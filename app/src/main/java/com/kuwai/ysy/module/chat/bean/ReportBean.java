package com.kuwai.ysy.module.chat.bean;

public class ReportBean {

    private String reason;
    private boolean isCheck = false;

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
}
