package com.jafir.testdatabinding;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by jafir on 2017/5/3.
 */

public class MainHandler {
    private Context mContext;

    public MainHandler(Context context) {
        this.mContext = context;
    }

    public void click(View view){
        Toast.makeText(mContext, "onClickFriend", Toast.LENGTH_LONG).show();
        Button v  = (Button) view;
        v.setText("123213");
    }

    public void str(String s){
        Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
    }
}
