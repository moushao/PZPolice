package com.pvirtech.pzpolice.test.daggermvp;


import com.pvirtech.pzpolice.test.daggermvp.module.MyModule;

import dagger.Component;

/**
 * Created by 尤鹏达 on 2016/11/25.
 * 邮箱：3340418505@qq.com
 */

@Component(modules = MyModule.class)
public interface MyComponent {
    void inject(MvpDaggerActivity activity);
}
