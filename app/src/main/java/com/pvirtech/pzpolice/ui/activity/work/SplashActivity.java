package com.pvirtech.pzpolice.ui.activity.work;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.UserInfo;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.AppLoginActivity;
import com.pvirtech.pzpolice.main.AppValue;
import com.pvirtech.pzpolice.ui.activity.bottomnavigationbar.HomeMainActivity;
import com.pvirtech.pzpolice.utils.PreferenceUtils;

import java.io.File;

import es.dmoral.toasty.Toasty;

import static com.pvirtech.pzpolice.utils.PreferenceUtils.getPrefString;


/**
 * 开屏页
 */
public class SplashActivity extends Activity {

    private static final int sleepTime = 2000;
    private Context mContext;

    @Override
    protected void onCreate(Bundle arg0) {
        setContentView(R.layout.em_activity_splash);
        super.onCreate(arg0);
        mContext = SplashActivity.this;
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
        TextView versionText = (TextView) findViewById(R.id.tv_version);

        //        versionText.setText(getVersion());
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        rootLayout.startAnimation(animation);
        String ip = PreferenceUtils.getPrefString(mContext, "ipconfig", "");
        if (!TextUtils.isEmpty(ip))
            RetrofitHttp.SERVER_URL = ip;

    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            public void run() {
                initDirPath();

                long start = System.currentTimeMillis();
                boolean isAutoLogin = PreferenceUtils.getPrefBoolean(mContext, "IsAutoLogin", false);
                String time = getPrefString(mContext, "IsAutoLoginTime", null);
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, AppLoginActivity.class);

                if (isAutoLogin && !TextUtils.isEmpty(time)) {
                    long costTime = System.currentTimeMillis() - start;
                    if (sleepTime - costTime > 0) {
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    int days = (int) ((System.currentTimeMillis() - Long.valueOf(time)) / 86400000);
                    if (days < 6) {
                        Gson gson = new Gson();
                        String userInfo = PreferenceUtils.getPrefString(mContext, "userInfo", "");
                        if (!TextUtils.isEmpty(userInfo)) {
                            AppValue.getInstance().setmUserInfo(gson.fromJson(userInfo, UserInfo.class));
                        }
                        intent.setClass(SplashActivity.this, HomeMainActivity.class);
                    }
                }
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
                if (TextUtils.isEmpty(AppValue.getInstance().getmUserInfo().getUSER_ID()) || TextUtils.isEmpty
                        (AppValue.getInstance().getmUserInfo()
                                .getROLE_ID())) {
                    intent.setClass(SplashActivity.this, AppLoginActivity.class);
                }
                startActivity(intent);
                finish();
            }

        }).start();


    }

    private void initDirPath() {
        try {
            //            File efile = getExternalFilesDir(null);
            // 缓存目录
            File cfile = getExternalCacheDir();
            if (cfile != null) {
                String epath = cfile.getAbsolutePath();
                // 数据库目录
                String dbPath = getDirPath(epath, "/db", "数据");
                AppValue.getInstance().setDbPath(dbPath);
                // 图片目录
                String imgPath = getDirPath(epath, "/img", "照片");
                AppValue.getInstance().setImgPath(imgPath);
                // APP安装包下载目录
                String updatePath = getDirPath(epath, "/update", "更新");
                AppValue.getInstance().setUpdatePath(updatePath);
            } else {
                Toasty.error(mContext, "SD卡未加载，不能保存").show();
            }
        } catch (Exception e) {
        }
    }

    private String getDirPath(String epath, String path, String notice) {
        String dirPath = epath + path;
        File file = new File(dirPath);
        boolean ok = file.exists();
        if (!ok) {
            ok = file.mkdir();
        }
        if (ok) {
            return dirPath;
        } else {
            if (!TextUtils.isEmpty(notice)) {
                Toasty.error(mContext, "目录获取失败，" + notice + "不能保存").show();
            }
            return null;
        }
    }
}
