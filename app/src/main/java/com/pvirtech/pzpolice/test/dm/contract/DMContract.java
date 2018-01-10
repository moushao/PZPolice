package com.pvirtech.pzpolice.test.dm.contract;

/**
 * Created by 尤鹏达 on 2016/11/28.
 * 邮箱：3340418505@qq.com
 */

public class DMContract {
public interface View{
     void onClearText();
     void onLoginResult(Boolean result, int code);
}

public interface Presenter{

     void clear() ;
     void doLogin(String name, String password) ;
}

public interface Model{
}


}