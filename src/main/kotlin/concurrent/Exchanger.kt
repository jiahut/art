package concurrent

import java.util.concurrent.Exchanger

/**
 *  交换器 一个线程等待与另一个线程同时进行交换
 */
fun main(args: Array<String>) {
    val exchanger = Exchanger<String>()
    Seller(exchanger).start()
    Thread.sleep(1000)
    Buyer(exchanger).start()
}

class Buyer(private val exchanger: Exchanger<String>) : Thread() {
    override fun run() {
        val exchange = exchanger.exchange("100")
        println("thread $name buy $exchange")
    }
}

class Seller(private val exchanger: Exchanger<String>) : Thread() {

    override fun run() {
        println("waiting for buyer comming")
        val exchange = exchanger.exchange("apple")
        println("thread $name sell with $exchange money ")
    }
}
