package concurrent

import java.util.concurrent.Executors
import java.util.concurrent.Semaphore
import java.util.concurrent.locks.ReentrantLock

/**
 *  读写者问题
 *  主要为读者提供信息的共享资源，偶尔会被写者更新
 *  既要考虑系统的吞吐量，又要防止饥饿和陈旧资源得不到更新
 *
 */
fun main(args: Array<String>) {
    val r = Semaphore(5)
    val w = Semaphore(2)
    val d = Book(r, w, "book")
    var pool = Executors.newFixedThreadPool(7)
    (1..100).forEach {
        pool.submit(Reader(d))
        if (it % 20 == 0) {
            pool.submit(Writer(d))
        }
    }
    pool.shutdown()
}

data class Book(val rmutex: Semaphore, val wmutex: Semaphore, @Volatile var name: String) {
    // 只能同时有一个对文章进行修改
    val wlock = ReentrantLock()
    // 读者的容量
    val maxRmutex = rmutex.availablePermits()
}

class Reader(private val book: Book) : Runnable {
    override fun run() {
        book.rmutex.acquire()
        println(Thread.currentThread().name + "read.. ${book.name}")
        Thread.sleep(1)
        book.rmutex.release()
    }
}

class Writer(private val book: Book) : Runnable {
    override fun run() {
        book.wmutex.acquire()
        book.rmutex.acquire(book.maxRmutex)
        Thread.sleep(1)
        book.wlock.lock()
        book.name += "."
        println(Thread.currentThread().name + "write ...")
        book.wlock.unlock()
        book.rmutex.release(book.maxRmutex)
        book.wmutex.release()
    }
}
