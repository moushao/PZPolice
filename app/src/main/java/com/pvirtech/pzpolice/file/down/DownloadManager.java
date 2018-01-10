package com.pvirtech.pzpolice.file.down;

import android.text.TextUtils;

import com.pvirtech.pzpolice.enumeration.FileEnum;

import java.io.File;
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
public class DownloadManager {
    ReadWriteLock rwl = new ReentrantReadWriteLock();
    private String TAG = "DownloadManager";

    private List<DownloadInfo> mDownloadInfoList = new ArrayList<>();   //维护了所有下载任务的集合
    private String mTargetFolder;                   //下载目录
    private static DownloadManager mInstance;       //使用单例模式

    public static DownloadManager getInstance() {
        if (null == mInstance) {
            synchronized (DownloadManager.class) {
                if (null == mInstance) {
                    mInstance = new DownloadManager();
                }
            }
        }
        return mInstance;
    }


    /**
     * 得到所有下载任务
     */
    public List<DownloadInfo> getAllTask() {
        Lock lock = rwl.readLock();
        lock.lock();
        try {

            return mDownloadInfoList;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 添加一个下载任务
     */
    public boolean addTask(DownloadInfo downloadInfo) {
        Lock lock = rwl.writeLock();
        lock.lock();
        try {
            mDownloadInfoList.add(downloadInfo);
            return true;

        } finally {
           lock.unlock();
        }
    }

    /**
     * 取出一个下载任务
     */
    public DownloadInfo removeTask() {
        Lock lock = rwl.writeLock();
        lock.lock();
        try {
            DownloadInfo task = null;
            if (mDownloadInfoList != null && mDownloadInfoList.size() > 0) {
                for (DownloadInfo downloadInfo : mDownloadInfoList) {
                    if (downloadInfo.getState() == FileEnum.WAITING.getValue() || downloadInfo.getState() == FileEnum.ERROR.getValue()) {
                        downloadInfo.setState(FileEnum.DOING.getValue());
                        task = downloadInfo;
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
    public boolean deleteTask(DownloadInfo downloadInfo) {
        Lock lock = rwl.writeLock();
        lock.lock();
        try {
            if (mDownloadInfoList != null && mDownloadInfoList.size() > 0) {
                mDownloadInfoList.remove(downloadInfo);
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
        for (DownloadInfo downloadInfo : mDownloadInfoList) {
            addTask(downloadInfo);
        }
    }


    /**
     * 暂停全部任务,先暂停没有下载的，再暂停下载中的
     */
    public void pauseAllTask() {
        for (DownloadInfo info : mDownloadInfoList) {
            if (info.getState() != FileState.DOING) info.setState(FileState.PAUSE);
        }
        for (DownloadInfo info : mDownloadInfoList) {
            if (info.getState() == FileState.DOING) info.setState(FileState.PAUSE);
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

    /**
     * 重新下载
     */
    public void restartTask(final String taskKey) {
      /*  final DownloadInfo downloadInfo = getDownloadInfo(taskKey);
        if (downloadInfo != null && downloadInfo.getState() == DOWNLOADING) {
            //如果正在下载中，先暂停，等任务结束后再添加到队列开始下载
            pauseTask(taskKey);
            threadPool.getExecutor().addOnTaskEndListener(new ExecutorWithListener.OnTaskEndListener() {
                @Override
                public void onTaskEnd(Runnable r) {
                    if (r == downloadInfo.getTask().getRunnable()) {
                        //因为该监听是全局监听，每次任务被移除都会回调，所以以下方法执行一次后，必须移除，否者会反复调用
                        threadPool.getExecutor().removeOnTaskEndListener(this);
                        //此时监听给空，表示会使用之前的监听，true表示重新下载，会删除临时文件
                        addTask(downloadInfo.getFileName(), downloadInfo.getTaskKey(), downloadInfo.getRequest(), downloadInfo
                                .getListener(), true);
                    }
                }
            });
        } else {
            pauseTask(taskKey);
            restartTaskByKey(taskKey);
        }*/
    }

    /**
     * 重新开始下载任务
     */
    private void restartTaskByKey(String taskKey) {
      /*  DownloadInfo downloadInfo = getDownloadInfo(taskKey);
        if (downloadInfo == null) return;
        if (downloadInfo.getState() != DOWNLOADING) {
            DownloadTask downloadTask = new DownloadTask(downloadInfo, true, downloadInfo.getListener());
            downloadInfo.setTask(downloadTask);
        }*/
    }

    /**
     * 获取一个任务
     */
    public DownloadInfo getDownloadInfo(String taskKey) {
     /*   for (DownloadInfo downloadInfo : mDownloadInfoList) {
            if (taskKey.equals(downloadInfo.getTaskKey())) {
                return downloadInfo;
            }
        }*/
        return null;
    }

    /**
     * 移除一个任务
     */
    private void removeTaskByKey(String taskKey) {
       /* ListIterator<DownloadInfo> iterator = mDownloadInfoList.listIterator();
        while (iterator.hasNext()) {
            DownloadInfo info = iterator.next();
            if (taskKey.equals(info.getTaskKey())) {
                DownloadListener listener = info.getListener();
                if (listener != null) listener.onRemove(info);
                info.removeListener();     //清除回调监听
                iterator.remove();         //清除任务
                break;
            }
        }*/
    }

    /**
     * 根据路径删除文件
     */
    private boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) return true;
        File file = new File(path);
        if (!file.exists()) return true;
        if (file.isFile()) return file.delete();
        return false;
    }
}