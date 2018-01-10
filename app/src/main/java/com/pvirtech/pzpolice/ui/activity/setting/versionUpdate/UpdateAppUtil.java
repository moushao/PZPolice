package com.pvirtech.pzpolice.ui.activity.setting.versionUpdate;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.entity.AppUpdateEntity;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.appupdate.AppUpdateManager;

import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Teprinciple on 2016/11/15.
 */
public class UpdateAppUtil {

    /**
     * 获取apk的版本号 currentVersionCode
     * @param ctx
     * @return
     */
    public static int getAPPLocalVersion(Context ctx) {
        int currentVersionCode = 0;
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            String appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersionCode;
    }

//    public static void getAPPServerVersion(Context context, final VersionCallBack callBack){
////        HttpUtil.getObject(Api.GETVERSION.mapClear().addBody(), VersionInfo.class, new HttpUtil.ObjectCallback() {
////            @Override
////            public void result(boolean b, @Nullable Object obj) {
////                if (b){
//////                        BaseApplication.getIntance().checkVersion();
////                        callBack.callBack((VersionInfo) obj);
////                }
////            }
////        });
//    }

    /**
     * 获取服务器的版本号
     * @param context  上下文
     * @param callBack 为了控制线程，需要获取服务器版本号成功的回掉
     */
    public static void getAPPServerVersion(Context context, final VersionCallBack callBack){
        //todo 自己的网络请求获取 服务器上apk的版本号（需要与后台协商好）
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("versionNum", getAPPLocalVersion(context));//versionNum
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        /**
         * 网路访问登录
         */
        RetrofitHttp.provideClientApi().getVersionCode(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<AppUpdateEntity>>() {
            @Override
            public void accept(@NonNull HttpResult<AppUpdateEntity> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    AppUpdateEntity updateEntity = (AppUpdateEntity) response.getData();
                    int versionCode = updateEntity.getCLIENT_VERSION();
                    String appApk = updateEntity.getDOWNLOAD_URL();
                    callBack.callBack(versionCode,appApk);
                } else {
                    L.d("失败");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d("失败");
            }
        });

    }

    /**
     * 更新APP
     * @param context
     */
    public static void updateApp(final Context context){
        getAPPServerVersion(context, new VersionCallBack() {
            @Override
            public void callBack(final int versionCode, final String appApk) {
                if (versionCode > getAPPLocalVersion(context)){
                    Log.i("this","版本信息：当前"+getAPPLocalVersion(context)+",服务器："+ versionCode);
                    if (versionCode > getAPPLocalVersion(context)){
                        ConfirmDialog dialog = new ConfirmDialog(context, new Callback() {
                            @Override
                            public void callback() {
                                //服务器apk path,这里放了百度云盘的apk 作为测试
                                String apkPath = "http://"+appApk;
                                DownloadAppUtils.downloadForAutoInstall(context, apkPath, "pvirtech.apk", "光明路派出所");
//                                AppUpdateManager.getInstance(context, apkPath, "").downloadAPK();
                            }
                        });
                        dialog .setContent("发现新版本\n是否下载更新?");
                        dialog.setCancelable(false);
                        dialog .show();
                    }
                }
                else
                    Toasty.success(context,"已经是最新版本",Toast.LENGTH_SHORT).show();
            }
        });


    }

    public interface VersionCallBack{
        void callBack(int versionCode,String appApk);
    }
}
