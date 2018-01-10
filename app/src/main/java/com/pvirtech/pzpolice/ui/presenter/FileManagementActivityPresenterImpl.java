package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.ui.contract.FileManagementActivityContract;
import com.pvirtech.pzpolice.ui.model.FileManagementActivityModelImpl;

/**
 * Created by Administrator on 2017/03/22
 */

public class FileManagementActivityPresenterImpl implements FileManagementActivityContract.Presenter {
    FileManagementActivityModelImpl model = new FileManagementActivityModelImpl();
    FileManagementActivityContract.View view;

    public FileManagementActivityPresenterImpl(FileManagementActivityContract.View view) {
        this.view = view;
    }
}