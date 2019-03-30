package com.kuwai.ysy.module.findtwo.bean;

public class GridBean {

    private String content;
    private int drawable;
    private int type;

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content == null ? "" : content;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public GridBean(String content, int drawable, int type) {
        this.content = content;
        this.drawable = drawable;
        this.type = type;
    }
}
