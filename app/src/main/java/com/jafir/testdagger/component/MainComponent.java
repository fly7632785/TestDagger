package com.jafir.testdagger.component;

import com.jafir.testdagger.di.ActivityScope;
import com.jafir.testdagger.ui.activity.MainActivity;
import com.jafir.testdagger.app.AppComponent;
import com.jafir.testdagger.module.MainModule;

import dagger.Component;

/**
 * Created by jafir on 2017/3/14.
 */
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {

    /**
     * inject一个activi或者一个fragment 必须是指定的、明确的
     *
     * @param activity
     */
    void inject(MainActivity activity);
//    void inject(Activity activity);  错误的例子
}
