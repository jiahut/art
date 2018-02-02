package concurrent

import java.util.*
import java.util.concurrent.Semaphore

/**
 * 信号量，通过计数器控制对共享资源的访问
 */

fun main(args: Array<String>) {

    val semaphore = Semaphore(5)
    val randow = Random()

    (1..8).forEach {
        Thread {
            semaphore.acquire()
            println("thread ${Thread.currentThread().name} acquire")
            val time = randow.nextInt(10)
            println("thread ${Thread.currentThread().name} sleep $time s")
            Thread.sleep(1000L * time)
            println("thread ${Thread.currentThread().name} release")
            semaphore.release()
        }.start()
    }

}