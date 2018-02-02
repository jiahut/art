package concurrent

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

/**
 *  线程安全的原子类型
 */
fun main(args: Array<String>) {
    var ai = AtomicInteger(0)
    var i = 0

    var pool = Executors.newFixedThreadPool(5)
    (1..10000).forEach {
        pool.submit {
            i++
            ai.incrementAndGet()
        }
    }
    pool.shutdown()

    while (!pool.isTerminated) {
    }
    println("the atomic value is ${ai.get()}")
    println("the non-atomic value is $i ")
}