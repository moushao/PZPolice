package com.pvirtech.pzpolice.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.telephony.TelephonyManager;

import com.pvirtech.pzpolice.db.GreenDaoContext;
import com.pvirtech.pzpolice.file.upload.DaoMaster;
import com.pvirtech.pzpolice.file.upload.DaoSession;
import com.pvirtech.pzpolice.third.baidu.LocationService;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * Created by pd on 2016/8/25.
 */
public class MyApplication extends android.app.Application {
    private static MyApplication instance = null;
    /***
     * 初始化定位sdk，建议在Application中创建
     */
    public LocationService locationService;
    public Vibrator mVibrator;

    private static DaoSession mDaoSession;

    public static MyApplication getInstance() {
        if (instance == null) {
            synchronized (MyApplication.class) {
                if (instance == null) {
                    instance = new MyApplication();
                }
            }
        }
        return instance;
    }

    private RefWatcher refWatcher;

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }

    public static RefWatcher getReWatcher(Context context) {
        MyApplication myApplication = (MyApplication) context.getApplicationContext();
        return myApplication.getRefWatcher();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        /*程序崩溃生成日志*/
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(this);
        /***
         * 初始化定位sdk，建议在Application中创建
         */
      /* locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());*/

        /**
         * 设置数据库
         */
        setDatabase();
        /**
         * 得到设备唯一ID
         */
        String deviceId = null;
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            deviceId = "0000000000000000000";
        }
        AppValue.getInstance().setStrDeviceId(deviceId);
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(new GreenDaoContext(this), "pz-db", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

}
