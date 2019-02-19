package channel

import java.util.*
import java.util.function.Predicate

// typealias SimpleSender = Sender<String, String>

object SenderManager {

    fun <T, R> createSenders(): MutableList<Sender<T, R>> {
        return mutableListOf<Sender<T, R>>();
    }
}


fun <T, R> List<Sender<T,R>>.send(action: (Sender<T, R>) -> Optional<R>) {
    this.forEach {
        action(it)
    }
}

fun <T, R> List<Sender<T, R>>.select(predicate: (Sender<T, R>) -> Boolean): List<Sender<T, R>> {
    return this.filter(predicate)
}

fun <T,R> MutableList<Sender<T,R>>.register(sender: Sender<T,R>) {
    this.add(sender)
}
