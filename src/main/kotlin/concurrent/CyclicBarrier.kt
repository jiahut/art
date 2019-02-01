package concurrent

import java.util.*
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.TimeUnit

/**
 *  循环屏障 等待多个线程都到达预定点时一起并发执行
 */
fun main(args: Array<String>) {

    val barrier = CyclicBarrier(5)

    val randow = Random()
    (1..5).forEach {
        Thread {
            println("thread ${Thread.currentThread().name} start")
            val timeOut = randow.nextInt(10) + 1
            println("thread ${Thread.currentThread().name} sleep $timeOut s")
            Thread.sleep(1000L * timeOut)
            println("thread ${Thread.currentThread().name} finish")
            barrier.await(2000, TimeUnit.MILLISECONDS)
//            barrier.await()
            println("current time is ${Date()}")
            println("thread ${Thread.currentThread().name} do other thing... ")
        }.start()
    }
}