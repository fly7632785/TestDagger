package com.jafir.testkoltlin

//直接使用id
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.jafir.testkoltlin.kotterknife.bindView
import com.jafir.testkoltlin.utils.log
import kotlinx.android.synthetic.main.activity_main.*


open class S {
    var v1: String? = null
    open fun m1() {}
    fun m2() = false
}

class SS : S() {
    //只有S是 open才能被继承  默认是final的
    //只有m1是open才能被重写
    override fun m1() {
        super.m1()//调用父类的方法
        //可以调用父类的成员变量
        super.v1 = "2"
        v1 = "2"
        m2()//false
    }
}

/**
 * 定义接口
 */
interface I1 {
    var id: Int //不能初始化
    //有{} 的不是抽象方法
   fun m1(name: String) {
        log(name)
    }

    //没有{}的是抽象方法 必须要实现
    fun m2(name: String)


}

class C : I1 {
    override fun m2(name: String) {
    }

    override var id: Int = 0

    var id2 = 1
    fun m1(name: String,s:Int) {

    }
    override fun m1(name: String) {

    }

    fun mm() {
        id = 1//可以用接口里面的id
        m1("jaifr")//可以调用接口里面的实现方法
        m2("jaifr")
        id2 = 2
    }
}

/**
 * 定义抽象类
 *
 */
abstract class AC {
    var f1 = 1
    abstract fun m1()
//    abstract fun m2(){不能实现方法
//
//    }

    open fun m2() { //可以定义非abstact方法 不用实现 加入open可以供 重写

    }
}

class CCC : AC() {

    //并不是静态成员变量
    @JvmField var staticF = 1
    var constantF = 2
//    const val a = 1  const只能在伴生对象里面使用

    /**
     * 伴生对象
     * 可以直接调用其内部属性和方法
     * 类似单例
     */
    companion object {

        val valF = 1
        const val constF = 1
        fun getAge() = {
            age
        }

        var age = 1

        @JvmField //用注解来表示静态成员变量
        var staticFF = 1

        @JvmStatic
        fun staticFun() = 1
    }

    //必须实现abstract 方法
    override fun m1() {
        m2()
    }

    override fun m2() {

    }

    /**
     * 必须写inner不然初始化不了
     */
    inner class IC() {
        fun m1() {

        }
    }


}

/**
 * 用object来实现 静态变量 和 方法
 * 还有单例
 */
object Singleon {
    private var age = 1
    fun getAge(): Int {
        return age.plus(1)
    }

    var name = "123"
}


data class Users(var id: Int, var name: String)


class MainActivity : AppCompatActivity() {
    fun mm() {
        var user = Users(1, "jafir")
        var (id, name) = user
        log("id:$id name:$name")

        var c: CCC.IC = CCC().IC()
        c.m1()

//        CCC.valF = 2 val 不可以被修改
//        CCC.constF = 2 const 不能被修改
        CCC.getAge()
        CCC.staticFF

        var cc: CCC = CCC()
        cc.staticF


        CCC.staticFun()

        Singleon.getAge()
        Singleon.name

    }


//    val firstName: TextView by bindView(R.id.first_name)
//    val lastName: TextView by bindView(R.id.last_name)
//
//    // Optional binding.
//    val details: TextView? by bindOptionalView(R.id.details)
//
//    // List binding.
//    val nameViews: List<TextView> by bindViews(R.id.first_name, R.id.last_name)
//
//    // List binding with optional items being omitted.
//    val nameViews: List<TextView> by bindOptionalViews(R.id.first_name, R.id.middle_name, R.id.last_name)


    val button1 by bindView<Button>(R.id.button)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mm()
        test()

        var c: C = C()
        c.id //调用接口中的成员变量
        c.id2//调用类中成员变量
        c.mm()//调用类中方法
        c.m1("j1")
        c.m2("j1")

        var user: User = User("", 0, 0, 0)
        user.age = 11

        var jafir = Jafir()
        var jafir1 = Jafir1(1)
        var jafir2 = Jafir2(1, 12)


        var jafircon = JafirCon("", 11, 1)//调用了  1 2 3个构造方法
        var jafircon1 = JafirCon("")//调用了 1构造方法
        var jafircon2 = JafirCon("", 22)//调用了 1 2 构造方法


        //直接使用id
        button1.text = "button"
        button1.setOnClickListener {  }
        //直接使用id
        my_button.text = "button1"
        my_button.setOnClickListener {
            testMe("click", 1, 2, 3)
            startActivity(Intent(this, Main2Activity::class.java))
        }

        //${}  里面可以执行语句
        Log.d("debug", "可以这样写：${testMe("test", 1, 2, 3)}")

        /**
         * 其他文件里面定义的方法 随处可调用
         */
        getUsername()

        attention()


        testArray()

        testFor()


        testWhen()

        testList()


        testOperator1()


    }

    /**
     *
     *
     * 总数操作符
     * any —— 判断集合中 是否有满足条件 的元素；
    all —— 判断集合中的元素 是否都满足条件；
    none —— 判断集合中是否 都不满足条件，是则返回true；
    count —— 查询集合中 满足条件 的 元素个数；
    reduce —— 从 第一项到最后一项进行累计 ；
    reduceRight —— 从 最后一下到第一项进行累计；
    fold —— 与reduce类似，不过有初始值，而不是从0开始累计；
    foldRight —— 和reduceRight类似，有初始值，不是从0开始累计；
    forEach —— 循环遍历元素，元素是it，可对每个元素进行相关操作；
    forEachIndexed —— 循环遍历元素，同时得到元素index(下标)；
    max —— 查询最大的元素，如果没有则返回null；
    maxBy —— 获取方法处理后返回结果最大值对应的那个元素的初始值，如果没有则返回null；
    min —— 查询最小的元素，如果没有则返回null；
    minBy —— 获取方法处理后返回结果最小值对应那个元素的初始值，如果没有则返回null；
    sumBy —— 获取 方法处理后返回结果值 的 总和；
    dropWhile —— 返回从第一项起，去掉满足条件的元素，直到不满足条件的一项为止
     */
    private fun testOperator1() {
        val list = listOf(1, 2, 3, 4, 5)
        val list1 = listOf(6, 7, 8, 9, 10)
        //是否有大于2的数
        log(list.any { it > 2 }.toString())
        log(list.count { it > 2 })

        log((list + list1).toString())
        log(listOf(list, list1).flatMap { it - 1 - 5 }.toString())

        log(list1.map { it }.toString())
        log(list1.map { it + 1 }.toString())

    }

    private fun testList() {

        val emptylist = emptyList<String>()
        val list = listOf("123", "123")
        val ll = mutableListOf(1, 2, 3)

        val set = setOf(123, 123)
        val hashset = hashSetOf(123, 123)
        val linkedSet = linkedSetOf(123, 123)
        val sortedSet = sortedSetOf(123, 123)


        /**
         * key to value
         * 类型同上都有 hash link sort mutable
         */
        val map = mapOf(1 to "1", 2 to "2")
        ll.any { it > 1 }

    }

    private fun testWhen() {
        var result = 80
        when (result) {
            100 -> log("s")
            90 -> log("a")
            in 80..90 -> log("b")
            else -> log("c")
        }

//        instance   as  is
        when (result) {
            is Int -> log("yse")
            else -> log("no")
        }


        val num: Int = try {
            val x: Int = 1 / 0
            x
        } catch (e: Exception) {
            log(e.toString())
            -1
        }

    }

    private fun testFor() {
        /**
         * scala to 表示xx到xx
         * 这里 ..表示 to
         */
        for (a in 1..10) log(a)
        //跳3
        for (a in 1..10 step 3) log(a)

        if ("a" in "abc") log("a 在 abc中")

        //倒叙
        for (i in 9 downTo 1) log(i)

        //倒叙
        val arrya3 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        for (i in arrya3.reversed()) log(i)


    }

    /**
     * 定义数组
     */
    private fun testArray() {
        val array1 = arrayOfNulls<Int>(10)
        val array2 = emptyArray<Int>()
        val arrya3 = Array(10, { num -> num })
        val a = arrayOf(1, 2, 3)
        val aa = intArrayOf(1, 2, 3)
        val b = booleanArrayOf(true, false)


    }

    /**
     * 1.没有自动向上转型，比如Int转Long，需要自己调toXxx方法转
    2.Long类型结尾必须为大写的L，不能为小写，比如1024L
    3.字符Char不是Number，用单引号来声明，比如’c’，不能像Java一样直接拿来当数字使，
    如果你想把Char的值给Int，需要调toInt()方法
    4.Boolean的值为true或false
    5.Kotlin不支持8进制，十六进制0x开头，二进制0b开头
    6.位运算符，Java中的与或运算符用：|和&，kotlin中使用or和and关键字来替代
    其他运算符也有分别的关键字替代：shl(有符号左移)，shr(有符号右移)，ushr(无符号右移)
    ，xor(按位异或)，inv(按位取反)
     */
    private fun attention() {
        val a: Int = 1
        val c: Long = 1123123
        val b: Long = a.toLong()

        /**
         * 可以直接返回 a  b  不用写return
         */
        val max = if (a < b) {
            a
        } else
            b


    }

    private fun test() {
//        val s : String = null.toString() 和下面相同
        val s: String = "null"
        //定义类型为空必须有？
        //如果使用的时候如果调用 s.toString()方法，只有s不为空的时候才会调用 否则不调用

        val ss: Int? = null

        Log.d("debug", s?.toString())
        Log.d("debug", "a" + ss?.toString())



        Log.d("debug", (true and false).toString())
        Log.d("debug", (true or false).toString())
        Log.d("debug", (1 and 2).toString())
        Log.d("debug", (1 shl 2).toString())
        Log.d("debug", (1 shr 2).toString())
        Log.d("debug", (1 or 2).toString())


    }

    /**
     * vararg 相当于java 的...
     */
    fun testMe(a: String, c: Int, vararg b: Int): Boolean {
        //这里$b直接就是变量b的值
        //这里b 有两个2参数 2 3 所以下面执行2次
        for (d in b) {
//            shortToast(this, "$a  $c  $d ")
        }
        return false
    }


}
