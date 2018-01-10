package com.pvirtech.pzpolice.ui.contract;

/**
 * Created by Administrator on 2017/1/5.
 */

public class WorkOneContract {

    public interface View {
        void getDataSucessed(String data);

        void getDataFailed(String data);
    }

    public interface Presenter {
        void getData();
    }

    public interface Model {
        void setName(String s);

        void setPasswd(String s);

        String getName();

        String getPasswd();

        boolean checkUserValidity();
    }


}