package com.pvirtech.pzpolice.test.dm.component;


import com.pvirtech.pzpolice.test.dm.DMLoginActivity;
import com.pvirtech.pzpolice.test.dm.module.DMLoginModule;

import dagger.Component;
@Component(modules = DMLoginModule.class)
public interface DMLoginActivityComponent {
     void inject(DMLoginActivity activity);
}
