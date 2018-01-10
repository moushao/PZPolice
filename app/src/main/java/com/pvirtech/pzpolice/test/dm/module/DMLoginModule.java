package com.pvirtech.pzpolice.test.dm.module;


import com.pvirtech.pzpolice.test.dm.contract.DMContract;

import dagger.Module;
import dagger.Provides;


@Module
public class DMLoginModule {

    private final DMContract.View view;

    public DMLoginModule(DMContract.View view) {
        this.view = view;
    }

    @Provides
    DMContract.View provideILogView() {
        return view;
    }

}
