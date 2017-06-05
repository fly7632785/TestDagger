package com.jafir.testkoltlin

import android.app.Activity
import android.widget.Toast
import com.jafir.testkoltlin.utils.log

/**
 * Created by jafir on 2017/4/13.
 */
class Jafir {
    var name: String? = null

    constructor()

    init {
        name = ""
    }

    fun m2(){

    }

}


class Jafir1 constructor(id: Int) {
    init {
        log(id)
        //实例化对象 调用方法
        var jafir :Jafir = Jafir()
        jafir.m2()
    }
}

/**
 * 使用默认的值 170
 */
class Jafir2(var age: Int, var height: Int = 170) {
    init {
        log("age :$age  height:$height")
    }
}


class Jafir22 (s : Int){
    fun f(){

    }

}

class JafirCon {
    var name: String = ""
    private var age: Int = 0
    private var height: Int = 0




    constructor(name: String = "jafir") {
        this.name = name
        log(111111)
        val jafir2 = Jafir22(1)
    }


    constructor(name: String, age: Int) : this(name) {
//        this.name = name  可以由 ：this(name) 替换
        /**
         * 先调用 this 然后在调用 this.age =age
         */
        this.age = age
        log(222222222)
    }

    /**
     * 会自动调用2个参数的 构造器
     */
    constructor(name: String, age: Int, height: Int) : this(name, age) {
        this.height = height
        log(33333333)
    }

}

/**
 * 扩展函数 表示只有Activity才能调用
 */
fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

//
//interface Animal{
//    fun bark()
//}
//
//class Dog :Animal {
//    override fun bark() {
//        println("Wang Wang")
//    }
//}
////class Cat(animal: Animal) : Animal  {
////    override fun bark() {
////    }
////}
//
////如果不用代理的话 就需要写成上面那样  必须要实现bark方法  代理的话 不需要实现，直接可调用
////代理对象的方法
//class Cat(animal: Animal) : Animal by animal {
//}
//
//fun main(args: Array<String>) {
//    Cat(Dog()).bark()
//}

interface Animal{
    fun bark()
}

interface Food{
    fun eat()
}

class Delegate : Animal, Food {
    override fun eat() {
        println("mouse")
    }

    override fun bark() {
        println("Miao")
    }
}

class Cat(animal: Animal, food: Food) : Animal by animal, Food by food {
}

 fun main(args: Array<String>) {
    val delegate: Delegate = Delegate()
    Cat(delegate, delegate).bark()
    Cat(delegate, delegate).eat()


}
