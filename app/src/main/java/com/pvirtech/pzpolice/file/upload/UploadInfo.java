package com.pvirtech.pzpolice.file.upload;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * ================================================
 * 作    者：尤鹏达
 * 版    本：1.0
 * 创建日期：2016/11/11
 * 描    述：全局的下载监听
 * 修订历史：
 * ================================================
 */
@Entity
public class UploadInfo {

    @Id
    private Long id;
    private String targetPath;                  //保存文件地址
    private String fileName;                    //保存的文件名
    private long uploadLength;                  //已上传大小
    private long totalLength;                   //总大小
    //    @Transient
    private long networkSpeed;                  //上传速度
    private int state;                          //当前状态

    @Generated(hash = 938476500)
    public UploadInfo(Long id, String targetPath, String fileName,
                      long uploadLength, long totalLength, long networkSpeed, int state) {
        this.id = id;
        this.targetPath = targetPath;
        this.fileName = fileName;
        this.uploadLength = uploadLength;
        this.totalLength = totalLength;
        this.networkSpeed = networkSpeed;
        this.state = state;
    }

    @Generated(hash = 837649493)
    public UploadInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTargetPath() {
        return this.targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getUploadLength() {
        return this.uploadLength;
    }

    public void setUploadLength(long uploadLength) {
        this.uploadLength = uploadLength;
    }

    public long getTotalLength() {
        return this.totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public long getNetworkSpeed() {
        return this.networkSpeed;
    }

    public void setNetworkSpeed(long networkSpeed) {
        this.networkSpeed = networkSpeed;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

}