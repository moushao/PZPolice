package com.pvirtech.pzpolice.db;
/**
 *
 */

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import es.dmoral.toasty.Toasty;

/**
 * ******************************************************************************
 * <p/>
 * Project Name  : CHPolice
 * Package       : com.pvirtech.police.ch.data.db
 * <p/>
 * <p/>
 * Copyright 派沃特科技
 * <p/>
 * <Pre>
 * TODO
 * </Pre>
 * Created by Justice on 2017/5/16.
 * <p/>
 * <p/>
 * ********************************************************************************
 */
public class GreenDaoContext extends ContextWrapper {

    private String currentUserId = "chga";//一般用来针对一个用户一个数据库，以免数据混乱问题
    private Context mContext;

    public GreenDaoContext(Context context) {
        super(context);
        this.mContext = context;
//        this.currentUserId = "greendao";//初始化
    }

    /**
     * 获得数据库路径，如果不存在，则创建对象
     *
     * @param dbName
     */
    @Override
    public File getDatabasePath(String dbName) {
        String dbDir = getDataBasePath();
//        String dbDir = FileUtils.DB_DIR_EXTERNAL;
        if (TextUtils.isEmpty(dbDir)) {
            Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
            return null;
        }
        File baseFile = new File(dbDir);
        // 目录不存在则自动创建目录
        if (!baseFile.exists()) {
            baseFile.mkdirs();
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(baseFile.getPath());
        buffer.append(File.separator);
//        buffer.append(currentUserId);
        dbDir = buffer.toString();// 数据库所在目录
        buffer.append(File.separator);
//        buffer.append(dbName+"_"+currentUserId);//也可以采用此种方式，将用户id与表名联系到一块命名
        buffer.append(dbName);
        String dbPath = buffer.toString();// 数据库路径
        // 判断目录是否存在，不存在则创建该目录
        File dirFile = new File(dbDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        // 数据库文件是否创建成功
        boolean isFileCreateSuccess = false;
        // 判断文件是否存在，不存在则创建该文件
        File dbFile = new File(dbPath);
        if (!dbFile.exists()) {
            try {
                isFileCreateSuccess = dbFile.createNewFile();// 创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            isFileCreateSuccess = true;
        // 返回数据库文件对象
        if (isFileCreateSuccess)
            return dbFile;
        else
            return super.getDatabasePath(dbName);
    }

    public String getAppExternalFilesDir() {

        File efile = Environment.getExternalStorageDirectory();
        if (efile != null) {
            return efile.getAbsolutePath();
        }
        return null;
    }

    public static String getSDPath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().toString();
        } else {
            return "";
        }
    }

    public static String getDataPath() {
        boolean sdCardExist = Environment.getDataDirectory().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            return Environment.getDataDirectory().toString();
        } else {
            return "";
        }
    }

    public static String getDBPath() {
        String sdCardPath = getSDPath();
        if (TextUtils.isEmpty(sdCardPath)) {
            return "";
        } else {
            return sdCardPath + File.separator + "CHGA"
                    + File.separator + "db";
        }
    }

    public String getDataBasePath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            Toasty.warning(mContext, "SD卡不存在，请检查").show();
        }
        File cfile = getExternalCacheDir();
        if (cfile != null) {
            String path = cfile.getAbsolutePath() + File.separator + "db";
            return path;
        }
        return "";
    }

    /**
     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     *
     * @param name
     * @param mode
     * @param factory
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        return result;
    }

    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @param name
     * @param mode
     * @param factory
     * @param errorHandler
     * @see ContextWrapper#openOrCreateDatabase(String, int,
     * SQLiteDatabase.CursorFactory,
     * DatabaseErrorHandler)
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);

        return result;
    }

}
