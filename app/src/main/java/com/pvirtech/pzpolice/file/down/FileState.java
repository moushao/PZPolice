package com.pvirtech.pzpolice.file.down;

/**
 * ================================================
 * 作    者：尤鹏达
 * 版    本：1.0
 * 创建日期：2016/11/11
 * 描    述：全局的下载监听
 * 修订历史：
 * ================================================
 */

public class FileState {
    //定义下载状态常量
    public static final int NONE = 0;         //无状态  --> 等待
    public static final int WAITING = 1;      //等待    --> 下载，暂停
    public static final int DOING = 2;  //下载中  --> 暂停，完成，错误
    public static final int PAUSE = 3;        //暂停    --> 等待，下载
    public static final int FINISH = 4;       //完成    --> 重新下载
    public static final int ERROR = 5;        //错误    --> 等待
}
