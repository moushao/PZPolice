package com.pvirtech.pzpolice.third;

/**
 * Created by Administrator on 2017/3/21.
 */

public class RefreshEvent {
    public String message;

    public RefreshEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
