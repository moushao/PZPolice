package com.pvirtech.pzpolice.test.daggermvp.module;


import com.pvirtech.pzpolice.test.daggermvp.contract.MyContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MyModule {

    private final MyContract.View view;

    public MyModule(MyContract.View view) {
        this.view = view;
    }

    @Provides
    MyContract.View providerView() {
        return view;
    }
}
