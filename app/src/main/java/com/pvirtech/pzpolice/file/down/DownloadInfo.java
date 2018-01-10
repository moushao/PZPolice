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
public class DownloadInfo {


    private String url;                 //文件URL
    private String targetFolder;        //保存文件夹
    private String targetPath;          //保存文件地址
    private String fileName;            //保存的文件名
    private long totalLength;           //总大小
    private long downloadLength;        //已下载大小
    private long networkSpeed;          //下载速度
    private int state = 0;              //当前状态
    public DownloadInfo() {
    }

    public DownloadInfo(String url, String targetFolder, String targetPath, String fileName, long totalLength, long
            downloadLength, long networkSpeed, int state) {
        this.url = url;
        this.targetFolder = targetFolder;
        this.targetPath = targetPath;
        this.fileName = fileName;
        this.totalLength = totalLength;
        this.downloadLength = downloadLength;
        this.networkSpeed = networkSpeed;
        this.state = state;
       /* this.listener = listener;*/
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargetFolder() {
        return targetFolder;
    }

    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public long getDownloadLength() {
        return downloadLength;
    }

    public void setDownloadLength(long downloadLength) {
        this.downloadLength = downloadLength;
    }

    public long getNetworkSpeed() {
        return networkSpeed;
    }

    public void setNetworkSpeed(long networkSpeed) {
        this.networkSpeed = networkSpeed;
    }

    /*public DownloadListener getListener() {
        return listener;
    }

    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }*/


}