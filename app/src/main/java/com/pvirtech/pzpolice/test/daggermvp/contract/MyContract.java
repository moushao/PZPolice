package com.pvirtech.pzpolice.test.daggermvp.contract;

/**
 * Created by 尤鹏达 on 2016/11/22.
 * 邮箱：3340418505@qq.com
 */

public class MyContract {

    public interface View {
         void onClearText();

         void onLoginResult(Boolean result, int code);
    }

    public interface Presenter {
        void clear();

        void doLogin(String name, String passwd);
    }

    public interface Model {
        void setName(String s);

        void setPasswd(String s);

        String getName();

        String getPasswd();

        int checkUserValidity(String name, String passwd);
    }


}