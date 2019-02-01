package concurrent

import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock

/**
 * 可重入锁, 同一个线程可多次获取并释放锁
 * 实现synchronized的语义，可设置条件，拥有更好的灵活性
 */

fun main(args: Array<String>) {
    val takePool = Executors.newFixedThreadPool(5)
    val putPool = Executors.newFixedThreadPool(2)
    val queue = ProductQueue<String>(10)
    (1..100).forEach {

        putPool.submit {
            queue.put("hello ${it}")
            println("put the ${it}")
        }

        takePool.submit {
            var t = queue.take()
            println("take the ${t}")
        }

    }
    takePool.shutdown()
    putPool.shutdown()
}

class ProductQueue<T>(private var capacity: Int = 10) {
    private var items: Array<T> = arrayOfNulls<Any>(capacity) as Array<T>;
    private val lock = ReentrantLock()
    private var fullCond = lock.newCondition()
    private var emptyCond = lock.newCondition()
    private var head: Int = 0
    private var tail: Int = 0
    private var count: Int = 0

    fun put(t: T) {
        lock.lock()
        try {
            while (count == capacity) {
                fullCond.await()
            }
            items[tail] = t
            if (++tail == capacity) {
                tail = 0
            }
            ++count
            emptyCond.signalAll()
        } finally {
            lock.unlock()
        }
    }

    fun take(): T {
        lock.lock()
        try {
            while (count == 0) {
                emptyCond.await()
            }
            val t = items[head]
            if (++head == capacity) {
                head = 0
            }
            --count
            fullCond.signalAll()
            return t
        } finally {
            lock.unlock()
        }
    }

}