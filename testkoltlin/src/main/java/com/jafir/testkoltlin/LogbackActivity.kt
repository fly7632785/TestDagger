package com.jafir.testkoltlin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import org.slf4j.LoggerFactory


class LogbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logback)
        test()
    }

    private fun test() {


        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //has permission, do operation directly

        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1)
        }


        val log = LoggerFactory.getLogger(LogbackActivity::class.java)
        log.info("hello world")
        log.info("123")
        log.info("test world")
        log.debug("tdeee")
        log.error("tdee213e")
        log.warn("tdee1231e")
        log.debug("tdee213e")
        log.debug("tdee321e")
        log.trace("dfadsfasdfld")
        log.trace("dfads12312fasdfld")
        log.trace("dfadsfasdfld")
        log.trace("trace")


    }


    class A<in T> {
        fun m1(p: T) {

        }
    }

    abstract class AA<out T> {
        abstract fun m1(): T
    }

    class T1est {
        fun m() {

            val aa: A<Number> = A()
            aa.m1(1)

            //只能接受Double的父类   称为逆变     in
            val d: A<Double> = aa

//            val c:A<User> = aa

        }

        fun m2(a: AA<Int>) {
            val o: AA<Number> = a
//            不通过编译  因为Double不是 Int的父类
//            这里只能Number的子类  称为协变 out
//            val o1 :AA<Double> = a

            val oo: T1est = T1est()
            oo.add(1)
//            add(1.0)
        }


        fun <T : Int> add(a: T) {

        }
    }

}
