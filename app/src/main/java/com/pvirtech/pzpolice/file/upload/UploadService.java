package com.pvirtech.pzpolice.file.upload;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.pvirtech.pzpolice.enumeration.FileEnum;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.MyApplication;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class UploadService extends Service {
    private String TAG = "UploadService";
    private int THREAD_COUNT = 1;
    private ExecutorService fixedThreadPool = null;
    private OkHttpClient client = new OkHttpClient();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UploadInfoDao dao = MyApplication.getInstance().getDaoSession().getUploadInfoDao();
        List<UploadInfo> delete = dao.queryBuilder().where(UploadInfoDao.Properties.State.notIn(FileEnum.WAITING.getValue(), FileEnum.DOING
                        .getValue(),
                FileEnum.ERROR.getValue())).list();
        dao.deleteInTx(delete);
        List<UploadInfo> data = queryData();
        UploadManager.getInstance().addTasks(data);
        fixedThreadPool = Executors.newFixedThreadPool(THREAD_COUNT);
      /*  for (int i = 0; i < THREAD_COUNT; i++) {
            fixedThreadPool.execute(upLoadThread);
        }*/
        fixedThreadPool.execute(upLoadThread);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     *
     */
    private Thread upLoadThread = new Thread(new Runnable() {
        public void run() {
            while (true) {
                try {

                    //查询数据库
                    List<UploadInfo> data = queryData();
                    UploadManager.getInstance().addTasks(data);
                    UploadInfo entity = UploadManager.getInstance().removeTask();
                    if (entity == null) {
                        Thread.sleep(5 * 1000);
                        continue;
                    }
                    upload(entity);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });


    private void upload(final UploadInfo entity) {
        UploadManager.getInstance().addCount();
        File file = new File(entity.getTargetPath());
        RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        String s = file.getName();
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
        RetrofitHttp.provideClientApiFile().upload(part).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull ResponseBody responseBody) throws Exception {
                UploadManager.getInstance().setState(entity, FileEnum.DOWN.getValue());
                UploadManager.getInstance().reduceCount();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                UploadManager.getInstance().setState(entity, FileEnum.ERROR.getValue());
                UploadManager.getInstance().reduceCount();
            }
        });

    }

    private List<UploadInfo> queryData() {
        UploadInfoDao dao = MyApplication.getInstance().getDaoSession().getUploadInfoDao();
        List<UploadInfo> data = dao.queryBuilder().where(UploadInfoDao.Properties.State.in(FileEnum.WAITING.getValue(), FileEnum.DOING.getValue(),
                FileEnum.ERROR.getValue())).list();
        return data;
    }

    private void uploadByCount() {
        final UploadInfo entity = UploadManager.getInstance().removeTask();
        //查询数据库
        List<UploadInfo> data = queryData();
        UploadManager.getInstance().addTasks(data);
        if (entity == null) {
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                uploadByCount();
            }
            uploadByCount();
            return;
        } else {
            File file = new File(entity.getTargetPath());
            if (file.exists()) {
                RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
                String s = file.getName();
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
                RetrofitHttp.provideClientApi().upload(part).subscribeOn(Schedulers.io()).observeOn
                        (AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull ResponseBody responseBody) throws Exception {
                        UploadManager.getInstance().setState(entity, FileEnum.DOWN.getValue());
                        uploadByCount();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        UploadManager.getInstance().setState(entity, FileEnum.ERROR.getValue());
                        uploadByCount();
                    }
                });
            } else {
                UploadManager.getInstance().setState(entity, FileEnum.NOTEXISTS.getValue());
                uploadByCount();
            }
        }

    }
}