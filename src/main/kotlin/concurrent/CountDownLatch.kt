package concurrent

import java.util.*
import java.util.concurrent.CountDownLatch

/**
 *  倒计时阀门, 等待指定数量的事件发生后继续运行
 */

fun main(args: Array<String>) {

    val latch = CountDownLatch(2);
    val randow = Random()

    (1..2).forEach {
        Thread {
            println("thread ${Thread.currentThread().name} begin")
            val time = randow.nextInt(10)
            println("thread ${Thread.currentThread().name} sleep $time s")
            Thread.sleep(5000L * time)
            println("thread ${Thread.currentThread().name} end")
            latch.countDown()
        }.start()
    }

    println("wait for latch to countdown....")
    latch.await()
    println("latch had been countdown.")
    println("goodbye.")

}
