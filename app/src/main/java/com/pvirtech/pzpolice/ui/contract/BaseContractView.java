package com.pvirtech.pzpolice.ui.contract;

/**
 * Created by youpengda on 2017/3/2.
 */

public interface BaseContractView {
    void submitting();

    void submitSuccess();

    void submitFailed();

    void showWarning(String data);

    /**
     * show loading message
     *
     * @param msg
     */
    void viewShowLoading(String msg);

    /**
     * hide loading
     */
    void viewHideLoading();

    /**
     * show error message
     */
    void viewShowError(String msg);

}
