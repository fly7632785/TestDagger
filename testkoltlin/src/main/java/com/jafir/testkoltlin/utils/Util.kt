package com.jafir.testkoltlin.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * Created by jafir on 2017/4/12.
 *
 *
 * 扩展方法  因为没有定义与类中
 *
 * 本地方法，定义在方法内部的方法，可以访问外部函数的私有成员！
 *
 */
fun shortToast(context: Context, msg: String) {
    val a = "是的"
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    fun innerM() {
        Log.d("debug", "本地方法，定义在方法内部的方法，可以访问外部函数的私有成员！ $a")
    }

    //只能自己内部调用自己的方法
    innerM()

}

fun log(msg: String) {
    Log.d("debug", msg)
}

fun log(msg: Int) {
    Log.d("debug", "" + msg)
}