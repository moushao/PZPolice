package com.pvirtech.pzpolice.main;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 尤鹏达 on 2016/11/22.
 * 邮箱：3340418505@qq.com
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = CrashHandler.class.getSimpleName();
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    public static final String DIR = Constant.PHONE_PATH + "ApdError";
    public static final String NAME = getDHMS() + ".txt";

    private CrashHandler() {
    }


    public static CrashHandler getInstance() {
        return INSTANCE;
    }


    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        System.out.println("uncaughtException");
        String info = null;
        ByteArrayOutputStream baos = null;
        PrintStream printStream = null;
        try {
            baos = new ByteArrayOutputStream();
            printStream = new PrintStream(baos);
            ex.printStackTrace(printStream);
            byte[] data = baos.toByteArray();
            for (int i = 0; i < data.length; i++) {
                if (data[i] == '\n')
                    data[i] = '\r';
                else if (data[i] == '\t')
                    data[i] = '\n';
            }
            info = new String(data);
            data = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (printStream != null) {
                    printStream.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        writeErrorLog(info);
    }

    protected boolean writeErrorLog(String info) {
        File dir = new File(DIR);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return false;
            }
        }
        File file = new File(dir, NAME);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getDHMS() {
        Long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd_HH:mm:ss");
        return format.format(new Date(time));
    }

}