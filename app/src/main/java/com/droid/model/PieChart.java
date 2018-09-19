package com.droid.model;

public class PieChart {
    /**
     * 扇形值
     */
    int num;

    /**
     * 扇形名称
     */
    String tip;

    /**
     * 扇形颜色值
     */
    int color;

    public PieChart(String tip, int num, int color) {
        this.num = num;
        this.tip = tip;
        this.color = color;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}


