package com.jafir.testkoltlin

/**
 * Created by jafir on 2017/5/27.
 */

//定义lambda表达式的 参数类型 和 返回类型
typealias FlatMap<S> = (S) -> S
typealias Map<R, S> = (R) -> S

/**
 * 操作一个类型 还是 返回这个类型
 */
fun <S> flatMap(data: S): S {
    return data
}

/**
 * 操作一个类型但是 返回别的类型
 */
fun <R, S> map(data: R): S {
    return data as S
}


/**
 * 第一个S:表示函数定义一个泛型
 * 第二个S:是Reduce这个类型所需要的泛型（这里它所需要的就是函数定义的泛型）
 * 第三个S:传入的参数的Reduce类型所需要的泛型
 * 第四个S:返回的Reduce类型所需要的泛型
 */
fun <S> FlatMap<S>.flatMap(f: FlatMap<S>): FlatMap<S> = {
    this(f(it))//组合两个FlatMap类型的表达式
    f(this(it))
}

fun <R, S> Map<R, S>.map(m: Map<R, S>): Map<R, S> = {
    this(it)
}

fun main(args: Array<String>) {
    val result1 = { i: Int -> i * 3 }.flatMap {
        println(it * 2)
        it * 2
    }
    result1.invoke(100)//执行 *2 再执行 *3   200 600

    //闭包是一个 对象 用val  var 而不是fun
    val r: FlatMap<Int> = {
        println(it * 2)
        it * 2
    }
    println(r.invoke(10)) // 10 * 2  20

    val result: FlatMap<Int> = r.flatMap {
        println(it * 3)
        it * 3
    }
    result.invoke(5) // 先执行 5*3 在*2  15 30

    val m: Map<Int, String> = Int::toString
    val a: String = m.invoke(10)
    println(a)

    acceptFlatMap<Int> { i -> i+1}
//    acceptFlatMap<Int> { i -> i.toString() }
}
fun <S>acceptFlatMap(flatMap : FlatMap<S>){
}//接收的函数类型为FlatMap类型