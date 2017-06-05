package com.jafir.testkoltlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import cn.nekocode.kotgo.component.rx.RxBus
import com.google.gson.Gson
import com.jafir.testkoltlin.event.ClickEvent
import com.jafir.testkoltlin.kotterknife.bindView
import com.jafir.testkoltlin.utils.log
import com.jafir.testkoltlin.utils.shortToast
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main2.*
import org.jetbrains.anko.async
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread
import java.net.URL

class Main2Activity : AppCompatActivity() {
    /**
     * 注意 只能是val 不能var
     */
    val recy1cler: RecyclerView by bindView(R.id.my_recycler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        init()
    }

    private fun init() {
        val data: List<String> = arrayListOf("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1")
        my_recycler?.layoutManager = LinearLayoutManager(this)
        val adapter = MyAdapter(data)
        my_recycler?.adapter = adapter

        RxBus.toObserverable(ClickEvent::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    log("click event")
                }

        adapter.let {
            //let函数调用对象的方法

            //kotlin里面都没有用set方法  一切都是直接赋值 成员变量多是公开的
            it.onItemClickListener = { position -> shortToast(this, "click:$position") }
            it.onItemLongClickListener = {
                position ->
                shortToast(this, "longclick:$position")
                true//最后一行为返回值  不能加return
            }
        }

        val x = 16
        val s = "hell"

        val a = arrayOf(1, 2, 3)
        for (i in a.indices) {
//            log(i)
        }

        for (i in 0 until a.size) {

        }


        /**
         * 可以用来转化数据类型   在map里面  比如再.getXX()
         */
        val aa = (0 until a.size).map { a[it] }
        log("aaaaa::" + aa.toString())


        /**
         * 返回第一个符合条件的 ->对应
         */
        val valres = when {
            x in 1..10 -> "cheap"
            s.contains("hello") -> "it's a welcome!"
            else -> "no"
        }

        log(valres)

        async {
            log(Request("http://baidu.com").run())
            log(ForecastRequest("94040,us").execute().toString())
            uiThread { longToast("Request performed") }

        }

        /**
         * with可以把里面的对象分解为 对象里面的各个参数
         * 在{}各个参数 都可以 用
         * //with 对象的变量和方法 并且返回最后一行
         */
        with(User("jafir", 1, 1, 1)) {
            name = "11"
            age = 11
            age.inc() // age++
            age.dec()//age--
            log("jafir111".contains(name as String).toString())
            //+a
            log(age.unaryPlus())
            log(age.unaryMinus())
            1 //最后一行
        }.let {
            log("with:" + it.toString())
        }

        //let 使用it.xx方法 对象的变量和方法 并且返回最后一行
        User("jafir", 1, 1, 1).let {
            it.age = 1
            it.m1()
            1
        }.let {
            log("let:" + it.toString())
        }
        //apply 对象的变量和方法 并且返回自身对象
        User("jafir", 1, 1, 1).apply {
            age = 1
            m1()
            this.m1()
        }.let {
            log("apply:" + it.toString())
        }
        //run 对象的 变量和方法 并且返回最后一行的对象  这里返回1
        User("jafir", 1, 1, 1).run {
            age = 1
            m1()
            1
        }.let {
            log("run:" + it)
        }


    }

    class Request(val url: String) {
        fun run(): String {
            val forecastJsonStr = URL(url).readText()
            Log.d(javaClass.simpleName, forecastJsonStr)
            return forecastJsonStr
        }
    }

    class ForecastRequest(val zipCode: String) {
        companion object {
            private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
            private val URL = "http://api.openweathermap.org/data/2.5/" +
                    "forecast/daily?mode=json&units=metric&cnt=7"
            private val COMPLETE_URL = "$URL&APPID=$APP_ID&q="
        }

        fun execute(): ForecastResult {
            val forecastJsonStr = URL(COMPLETE_URL + zipCode).readText()
            return Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

