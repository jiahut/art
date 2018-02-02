package concurrent

import java.util.concurrent.Callable
import java.util.concurrent.Executors

/**
 *  异步获取结果
 */
fun main(args: Array<String>) {
    val pool = Executors.newFixedThreadPool(2)

    var acc1 = pool.submit(Accumulation(1, 100))
    var acc2 = pool.submit(Accumulation(100, 1000))
    println("acc1 = " + acc1.get())
    println("acc2 = " + acc2.get())
    pool.shutdown()

}

class Accumulation(private val start: Int, private val end: Int) : Callable<Int> {
    override fun call(): Int {
        var sum = 0
        var step = start
        while (end - step > 0) {
            sum += step
            step++
        }
        return sum
    }
}