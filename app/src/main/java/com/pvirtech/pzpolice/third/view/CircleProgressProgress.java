package com.pvirtech.pzpolice.third.view;

/**
 * Created by Administrator on 2017/3/29.
 */

public class CircleProgressProgress {
    private int progress;
    private int color=0xFFF3F3F3;

    public CircleProgressProgress(int progress, int color) {
        this.progress = progress;
        this.color = color;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
