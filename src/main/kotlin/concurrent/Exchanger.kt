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
        val money = "$100";
        val good = exchanger.exchange(money)
        println("thread $name buy $good with $money")
    }
}

class Seller(private val exchanger: Exchanger<String>) : Thread() {
    override fun run() {
        println("waiting for buyer comming")
        val good = "apple"
        val money = exchanger.exchange(good)
        println("thread $name sell $good with $money ")
    }
}
