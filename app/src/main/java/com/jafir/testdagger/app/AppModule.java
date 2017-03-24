package com.jafir.testdagger.app;

import android.content.Context;

import com.jafir.testdagger.model.User;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jafir on 2017/3/14.
 */

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context){
        this.context = context;
    }

    /**
     * 这里是把Context注解到AppContext里面去 所在在module里面来构造写入
     * 并且还是作为单例来使用
     * @return
     */
    @Singleton
    @Provides
    @Named("AppContext")
    Context provideContext(){
        return context;
    }


    @Provides
    @Singleton
    User provideUser(){
        return new User("jafir");
    }
}
