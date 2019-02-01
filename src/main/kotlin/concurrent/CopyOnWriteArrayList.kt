package concurrent

import java.util.concurrent.*;

/**
 *  写时复制列表
 *  不会有线程脏数据的产生
 */

fun main(args: Array<String>) {

    var list = CopyOnWriteArrayList<Double>()
//    var list = ArrayList<Double>()
    var pool = Executors.newFixedThreadPool(2)
    pool.execute {
        (1..10000).forEach {
            try {
                list.add(Math.random())
            } catch (e: ArrayIndexOutOfBoundsException) {
                println(Thread.currentThread().name)
                e.printStackTrace()
            }
        }
    }
    pool.execute {
        (1..10000).forEach {
            try {
                list.add(Math.random())
            } catch (e: ArrayIndexOutOfBoundsException) {
                println(Thread.currentThread().name)
                e.printStackTrace()
            }
        }
    }
    pool.shutdown()
    while (!pool.isTerminated) {
    }
    println(list.size)

}