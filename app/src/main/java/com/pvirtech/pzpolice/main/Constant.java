package com.pvirtech.pzpolice.main;

import java.io.File;

/**
 * Created by pd on 2016/10/8.
 */

public class Constant {
    public static String SUCCESS = "SUCCESS";
    public static String FALILED = "FALILED";
    public static String WORK_ON = " 09:00";
    public static String WORK_OFF = " 17:00";
    public static int PAGE_SIZE = 10;
    public static int MAXIMUM_CHARACTER_LENGTH = 30;
    public static final int RESULT_SELECT_PERSON = 90;//选人的code
    public static String PERSON_SELECTED = "listPersonSelected";
    public static String CASE_COME_TYPE = "caseComeType";
    public static String INTENT_BUNDLE = "data";
    public static String TITLE = "title";
    public static final String PHONE_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + "Pvirtech" + File.separator;


}
