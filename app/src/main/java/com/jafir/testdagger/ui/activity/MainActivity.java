package com.jafir.testdagger.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jafir.testdagger.R;
import com.jafir.testdagger.annotation.Jafir;
import com.jafir.testdagger.app.AppContext;
import com.jafir.testdagger.component.DaggerMainComponent;
import com.jafir.testdagger.component.MainComponent;
import com.jafir.testdagger.model.Father;
import com.jafir.testdagger.model.User;
import com.jafir.testdagger.utils.ImageProgresser;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.text);
        String s = new String("带划线的text");
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new StrikethroughSpan(),0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);


        textView.setText(spannableString);




        ImageView imageView = (ImageView) findViewById(R.id.image);
        ProgressBar progressBar = ImageProgresser.attachProgress(imageView);


        Context context = AppContext.get().getAppComponent().getContext();
        inject();


        Log.d("debug", "MainActivity user:" + user);
        Log.d("debug", "MainActivity user:" + AppContext.get().getAppComponent().getUser());
        Log.d("debug", "MainActivity getAppComponent:" + AppContext.get().getAppComponent());


        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OtherActivity.class));
            }
        });


        testStream();
        testDBflow();

        testAnnotation();
    }



    @Jafir(gender = Jafir.Gender.BOY)
    private void testDBflow() {


        User u1 = new User();
        u1.setName("jafir");
        u1.setFid(113l);
        u1.save();
        User u2 = new User();
        u2.setName("jafir2");
        u2.setFid(113l);
        u2.save();

        SQLite.select().from(User.class).queryList()
                .stream().forEach(item -> Log.d("debug", item.toString()));


        List<User> users = new ArrayList<>();
        users.add(u1);
        users.add(u2);

        Father f = new Father();
        f.setName("f1");
        f.setId(113l);
        f.setUser(u1);
//        f.setUsers(users);
        f.save();

        SQLite.select().from(Father.class).queryList()
                .stream().forEach(item -> Log.d("debug", item.toString()));

    }


    private void testAnnotation() {
        /**
         * 获取到使用了注解的类的方法
         */
        Method[] declaredMethods = MainActivity.class.getDeclaredMethods();
        for (Method method : declaredMethods) {

            /**
             * 然后通过此方法获得注解
             */
            Jafir jafir= method.getAnnotation(Jafir.class);
            if(jafir != null) {
                /**
                 * 然后通过注解获得注解里面的值
                 */
                Jafir.Gender gender = jafir.gender();
                Log.d("debug", "gender:" + gender.name());
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.N)
    private void testStream() {
        String[] ss = new String[]{"a", "b", "c", "d"};
        List l1 = Arrays.asList(ss);
        Stream s1 = l1.stream();
        String str1 = (String) s1.filter(item -> item.equals("b"))
                .collect(Collectors.joining(";"));

        List l2 = Arrays.asList(ss);
        Stream<String> s2 = l2.stream();
//        s2.forEach(System.out::println);
//        s2.peek(s->Log.d("debug1",s));  错误因为没有terminal操作符所以打印不出来
//        s2.peek(s->Log.d("debug1",s)).count();    peek相当于foreach但是不是一个terminal 所以可以后面继续写
//                                                  count是一个terminal操作符 可以打印
//        s2.forEach(s->Log.d("debug",s)); forEach直接就是terminal操作符

        Log.d("debug", str1);

        List l3 = Arrays.asList(ss);
        Stream<String> s3 = l3.stream();
        List ls1 = (List) s3.filter(item -> item != null)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        ls1.forEach(s -> Log.d("debug", (String) s));


    }

    private void inject() {
        Log.d("debug", "MainActivity getAppComponent:" + AppContext.get().getAppComponent());
        MainComponent maincomponent = DaggerMainComponent.builder()
                .appComponent(AppContext.get().getAppComponent())
                .build();
        maincomponent.inject(this);

    }


}
