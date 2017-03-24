package com.jafir.testdagger.component;

import com.jafir.testdagger.app.AppComponent;
import com.jafir.testdagger.di.ApplicationScope;
import com.jafir.testdagger.module.OtherModule;
import com.jafir.testdagger.ui.activity.OtherActivity;

import dagger.Component;

/**
 * Created by jafir on 2017/3/14.
 */
@ApplicationScope
@Component(modules = OtherModule.class, dependencies = AppComponent.class)
public interface OtherComponent {

    /**
     * inject一个activi或者一个fragment 必须是指定的、明确的
     *
     * @param activity
     */
    void inject(OtherActivity activity);
//    void inject(Activity activity);  错误的例子
}
