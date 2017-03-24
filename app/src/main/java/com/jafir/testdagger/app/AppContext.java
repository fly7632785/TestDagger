package com.jafir.testdagger.app;

import android.app.Application;
import android.util.Log;

import com.jafir.testdagger.model.User;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by jafir on 2017/3/14.
 */

public class AppContext extends Application {

    //把这个弄成注解 变量 在module里面提供构造
//    public static Context context;

    @Inject
    User user;

    @Getter//lombok生成get方法
    protected AppComponent appComponent;
    private static AppContext instance;

    public static AppContext get(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //init dbflow
        FlowManager.init(new FlowConfig.Builder(this).build());
        instance = this;
        intject();



        Log.d("debug","user:"+user);

    }

    /**
     * 注解引入module给component
     */
    private void intject() {
       appComponent =  DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.injetc(this);
    }


}
