package concurrent

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.Future
import java.util.concurrent.RecursiveTask

/**
 * 分而治之 大任务拆分成子任务并行的处理然后把结果合并起来
 */
fun main(args: Array<String>) {
    val forkJoin = ForkJoinPool()
    var result: Future<Int> = forkJoin.submit(CountTask(1, 10000))
    forkJoin.shutdown()
    println(result.get())
}

class CountTask(private var begin: Int, private var end: Int) : RecursiveTask<Int>() {
    override fun compute(): Int {
        var sum = 0
        if ((end - begin) <= 100) {
            var step = begin
            while (step <= end) {
                sum += step
                step++
            }
            return sum
        }
        val middle = (begin + end) / 2
        val leftFork = CountTask(begin, middle).fork()
        val rightFork = CountTask(middle + 1, end).fork()
        val left = leftFork.join()
        val right = rightFork.join()
        return left + right
    }
}