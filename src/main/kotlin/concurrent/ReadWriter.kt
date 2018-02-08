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

data class Book(val rmutex: Semaphore, val wmutex: Semaphore, var name: String) {
    val reader = ReentrantLock()
    val writer = ReentrantLock()
}

class Reader(private val book: Book) : Runnable {
    override fun run() {
        book.rmutex.acquire()
        book.writer.lock()
        Thread.sleep(2)
        println(Thread.currentThread().name + "read.. ${book.name}")
        book.writer.unlock()
        book.rmutex.release()

    }
}

class Writer(private val book: Book) : Runnable {
    override fun run() {
        book.wmutex.acquire()
        book.reader.lock()
        Thread.sleep(10)
        book.name += "."
        println(Thread.currentThread().name + "write ...")
        book.reader.unlock()
        book.wmutex.release()
    }
}
