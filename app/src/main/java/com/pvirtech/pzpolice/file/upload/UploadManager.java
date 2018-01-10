package com.pvirtech.pzpolice.file.upload;

import com.pvirtech.pzpolice.enumeration.FileEnum;
import com.pvirtech.pzpolice.file.down.FileState;
import com.pvirtech.pzpolice.main.MyApplication;
import com.pvirtech.pzpolice.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ================================================
 * 作    者：尤鹏达
 * 版    本：1.0
 * 创建日期：2016/11/11
 * 描    述：全局的下载监听
 * 修订历史：
 * ================================================
 */
public class UploadManager {
    ReadWriteLock rwl = new ReentrantReadWriteLock();
    private String TAG = "DownloadManager";
    List<UploadInfo> uploadInfoList = new ArrayList<>();
    private static UploadManager mInstance;       //使用单例模式
    private int currentCount = 0;

    public static UploadManager getInstance() {
        if (null == mInstance) {
            synchronized (UploadManager.class) {
                if (null == mInstance) {
                    mInstance = new UploadManager();
                }
            }
        }
        return mInstance;
    }

    public void addCount() {
        Lock lock = rwl.writeLock();
        lock.lock();
        try {
            currentCount++;
        } finally {
           lock.unlock();
        }
    }

    public void reduceCount() {
        Lock lock = rwl.writeLock();
        lock.lock();
        try {
            currentCount--;
        } finally {
            lock.unlock();
        }
    }

    private int getCurrentCount() {
        Lock lock = rwl.readLock();
        lock.lock();
        try {
            return currentCount;
        } finally {
           lock.unlock();
        }
    }

    public void setState(UploadInfo entity, int state) {
        Lock lock = rwl.writeLock();
        lock.lock();
        try {
            for (UploadInfo data : uploadInfoList) {
                if (entity.getId() == data.getId()) {
                    data.setState(state);
                    UploadInfoDao dao = MyApplication.getInstance().getDaoSession().getUploadInfoDao();
                    dao.update(data);
                    break;
                }
            }

        } finally {
           lock.unlock();
        }
    }

    /**
     * 得到所有下载任务
     */
    public List<UploadInfo> getAllTask() {
        Lock lock = rwl.readLock();
        lock.lock();
        try {

            return uploadInfoList;
        } finally {
          lock.unlock();
        }
    }

    /**
     * 添加一个下载任务
     */
    public boolean addTask(UploadInfo entity) {
        Lock lock = rwl.writeLock();
        lock.lock();
        try {
            uploadInfoList.add(entity);
            return true;

        } finally {
           lock.unlock();
        }
    }

    public boolean addTasks(List<UploadInfo> data) {
        Lock lock = rwl.writeLock();
        lock.lock();
        try {
            if (!ListUtils.isEmpty(data)) {
                if (ListUtils.isEmpty(uploadInfoList)) {
                    uploadInfoList.addAll(data);
                } else {
                    List<UploadInfo> adds = new ArrayList<>();
                    for (UploadInfo newData : data) {
                        boolean blnIsHave = false;
                        for (UploadInfo oldData : uploadInfoList) {
                            if (newData.getId() == oldData.getId()) {
                                blnIsHave = true;
                                break;
                            }
                        }
                        if (!blnIsHave) {
                            adds.add(newData);
                        }
                    }
                    uploadInfoList.addAll(adds);
                }
            }
            return true;
        } finally {
          lock.unlock();
        }
    }


    /**
     * 取出一个下载任务
     */

    public UploadInfo removeTask() {
        Lock lock = rwl.writeLock();
        lock.lock();
        try {
            UploadInfo task = null;

            System.out.println("getCurrentCount"+getCurrentCount());
            if (getCurrentCount() >= 2) {
                int a=getCurrentCount();
                return task;
            } else if (uploadInfoList != null && uploadInfoList.size() > 0) {
                for (UploadInfo entity : uploadInfoList) {
                    if (entity.getState() == FileEnum.WAITING.getValue() || entity
                            .getState() == FileEnum.ERROR.getValue()) {
                        entity.setState(FileEnum.DOING.getValue());
                        task = entity;
                        break;
                    }
                }
                return task;
            } else {
                return task;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 删除一个下载任务
     */
    public boolean deleteTask(UploadInfo entity) {
        Lock lock = rwl.writeLock();
        lock.lock();
        try {
            if (uploadInfoList != null && uploadInfoList.size() > 0) {
                uploadInfoList.remove(uploadInfoList);
                return true;
            } else {
                return false;
            }
        } finally {
           lock.unlock();
        }

    }


    /**
     * 开始所有任务
     */
    public void startAllTask() {
        for (UploadInfo entity : uploadInfoList) {
            addTask(entity);
        }
    }


    /**
     * 暂停全部任务,先暂停没有下载的，再暂停下载中的
     */
    public void pauseAllTask() {
        for (UploadInfo entity : uploadInfoList) {
            if (entity.getState() != FileState.DOING) entity.setState(FileState.PAUSE);
        }
        for (UploadInfo entity : uploadInfoList) {
            if (entity.getState() == FileState.DOING) entity.setState(FileState.PAUSE);
        }
    }

    /**
     * 停止
     */
    public void stopTask(final String taskKey) {
    /*    DownloadInfo downloadInfo = getDownloadInfo(taskKey);
        if (downloadInfo == null) return;
        //无状态和完成状态，不允许停止
        if ((downloadInfo.getState() != NONE && downloadInfo.getState() != FINISH) && downloadInfo.getTask() != null) {
            downloadInfo.getTask().stop();
        }*/
    }

    /**
     * 停止全部任务,先停止没有下载的，再停止下载中的
     */
    public void stopAllTask() {
    /*    for (DownloadInfo info : mDownloadInfoList) {
            if (info.getState() != DOWNLOADING) stopTask(info.getUrl());
        }
        for (DownloadInfo info : mDownloadInfoList) {
            if (info.getState() == DOWNLOADING) stopTask(info.getUrl());
        }*/
    }

    /**
     * 删除一个任务,会删除下载文件
     */
    public void removeTask(String taskKey) {
        removeTask(taskKey, false);
    }

    /**
     * 删除一个任务,会删除下载文件
     */
    public void removeTask(String taskKey, boolean isDeleteFile) {
        /*final DownloadInfo downloadInfo = getDownloadInfo(taskKey);
        if (downloadInfo == null) return;
        pauseTask(taskKey);                         //暂停任务
        removeTaskByKey(taskKey);                   //移除任务
        if (isDeleteFile) deleteFile(downloadInfo.getTargetPath());   //删除文件
        DownloadDBManager.INSTANCE.delete(taskKey);            //清除数据库*/
    }

    /**
     * 删除所有任务
     */
    public void removeAllTask() {
   /*     //集合深度拷贝，避免迭代移除报错
        List<String> taskKeys = new ArrayList<>();
        for (DownloadInfo info : mDownloadInfoList) {
            taskKeys.add(info.getTaskKey());
        }
        for (String url : taskKeys) {
            removeTask(url);
        }*/
    }


}