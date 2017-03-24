package com.jafir.testdagger.app;

import android.content.Context;

import com.jafir.testdagger.model.User;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jafir on 2017/3/14.
 * 这里作为单例 表示里面的变量都是单例存在的
 *
 * 这里可以关联多个module
 * 这样每个module里面的provide构造实例的方法就能够使用
 * 比如如果要使用retrofit的各种单例的xxxService就可以在这里获得
 *
 *
 */
@Singleton
@Component(modules = AppModule.class)
public  interface AppComponent {

    @Named("AppContext") Context getContext();

    User getUser();

    void injetc(AppContext context);
}
