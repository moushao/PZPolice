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
public interface  DownloadListener {
    /**
     * 开始任务时回调
     */
    public  void onStart();

    /**
     * 停止任务时回调
     */
    public  void onPuse();

    /**
     * 下载进行时回调
     */
    public  void onProgress(int progress);

    /**
     * 下载完成时回调
     */
    public  void onFinish();

    /**
     * 下载出错时回调
     */
    public  void onError(String errorMsg, Exception e);


}
