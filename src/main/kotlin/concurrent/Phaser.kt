package concurrent

import java.util.*
import java.util.concurrent.Phaser

/**
 *  相位调节器 可定义多个阶段的同步
 */

fun main(args: Array<String>) {

    val phaser = Phaser(1)
    val random = Random()
    (1..10).forEach {
        phaser.register()
        Thread {
            println("thread ${Thread.currentThread().name} is ready. ")
            phaser.arriveAndAwaitAdvance()
            // 所有线程都准备好了才开始
            println("thread ${Thread.currentThread().name} start")
            Thread.sleep(1000L * (random.nextInt(5) + 1))
            println("thread ${Thread.currentThread().name} end")
            phaser.arriveAndDeregister()
        }.start()
    }
    phaser.arriveAndDeregister()

    while (!phaser.isTerminated) {
    }
    println("game over");

}