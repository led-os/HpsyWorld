package com.kuwai.ysy.widget.shadow;

/**
 * description: 签到bean.
 *
 * @author Sorgs.
 * @date 2018/8/17.
 */
public class StepBean {
    /**
     * 未完成
     */
    public static final int STEP_UNDO = -1;
    /**
     * 正在进行
     */
    public static final int STEP_CURRENT = 0;
    /**
     * 已完成
     */
    public static final int STEP_COMPLETED = 1;

    private int state;
    private String number;

    public StepBean(int state, String number) {
        this.state = state;
        this.number = number;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
