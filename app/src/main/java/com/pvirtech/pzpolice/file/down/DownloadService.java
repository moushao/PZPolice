package com.pvirtech.pzpolice.file.down;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.pvirtech.pzpolice.main.AppValue;
import com.pvirtech.pzpolice.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ================================================
 * 作    者：尤鹏达
 * 版    本：1.0
 * 创建日期：2016/11/11
 * 描    述：全局的下载监听
 * 修订历史：
 * ================================================
 */
public class DownloadService extends Service {
    String TAG = "DownloadService";
    int THREAD_COUNT = 1;
    ExecutorService fixedThreadPool = null;
    OkHttpClient client;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        client = new OkHttpClient();
        DownloadInfo downloadInfo = new DownloadInfo("http://download.apk8.com/d2/soft/meilijia.apk", AppValue.getInstance()
                .getImgPath(), "aa",
                "apk1.apk", 0, 0, 0, 0);
        DownloadManager.getInstance().addTask(downloadInfo);
        DownloadInfo downloadInfo2 = new DownloadInfo("http://download.apk8.com/d2/soft/guoranfangbian.apk", AppValue.getInstance()
                .getImgPath(), "aa",
                "apk2", 0, 0, 0, 0);
        DownloadManager.getInstance().addTask(downloadInfo2);
        DownloadInfo downloadInfo3 = new DownloadInfo("http://download.apk8.com/d2/soft/GGzhushou.apk", AppValue
                .getInstance().getImgPath(), "aa",
                "apk3", 0, 0, 0, 0);
        DownloadManager.getInstance().addTask(downloadInfo3);
        fixedThreadPool = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
//            fixedThreadPool.execute(thread);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    Thread thread = new Thread(new Runnable() {
        public void run() {
            while (true) {
                try {
                    DownloadInfo downloadInfo = DownloadManager.getInstance().removeTask();
                    if (downloadInfo == null) {
                        Thread.sleep(2 * 1000);
                        continue;
                    }
                    down(downloadInfo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    private void down(final DownloadInfo downloadInfo) {
        Request request = new Request.Builder().url(downloadInfo.getUrl()).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                L.d("sucess");
                String path = downloadInfo.getTargetFolder() + "/" + downloadInfo.getFileName();
                downloadInfo.setTargetPath(path);
                L.i(TAG, "WriteFileManager.startToWrite.path:" + path);

                File futureFile = new File(path);
                InputStream inputStream = null;
                OutputStream outputStream = null;
                ResponseBody responseBody = response.body();
                long fileSize = responseBody.contentLength();
                downloadInfo.setTotalLength(fileSize);
                L.d(TAG, "WriteFileManager.writeResponseBodyToDisk.fileSize:" + fileSize);
                try {
                    long startTime;
                    long endTime;
                    byte[] fileReader = new byte[1024 * 1024];
                    long fileSizeDownloaded = 0;
                    inputStream = responseBody.byteStream();
                    outputStream = new FileOutputStream(futureFile);
                    while (downloadInfo.getState() == FileState.DOING) {
                        startTime = System.currentTimeMillis();

                        int read = inputStream.read(fileReader);
                        if (read == -1) {
                            break;
                        }
                        outputStream.write(fileReader, 0, read);
                        fileSizeDownloaded += read;
                        L.i(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                        downloadInfo.setDownloadLength(fileSizeDownloaded);
                        endTime = System.currentTimeMillis();

                        downloadInfo.setNetworkSpeed(1024 * 1000 / (endTime - startTime));
                    }
                    L.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    outputStream.flush();
                    downloadInfo.setState(FileState.FINISH);
                } catch (IOException e) {
                    L.d("IOException", "IOException");
                    downloadInfo.setState(FileState.ERROR);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}