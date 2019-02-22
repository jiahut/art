package channel

import java.lang.RuntimeException
import java.util.*
import java.util.function.Predicate

// typealias SimpleSender = Sender<String, String>

object SenderManager {

    fun <T, R> buildSenderList(): MutableList<Sender<T, R>> {
        return mutableListOf<Sender<T, R>>();
    }

}


fun <T, R> List<Sender<T,R>>.sendAll(action: (Sender<T, R>) -> Optional<R>) {
    this.forEach {
        action(it)
    }
}


fun <T, R> List<out Sender<T,R>>.sendByOrder(action: (Sender<T,R>) -> Optional<R>) {
    this.sortedBy {
        it as SendOrder
        it.order()
    }.forEach {
        action(it)
    }
}


fun <T, R> List<out Sender<T,R>>.sendTillSuccess(action: (Sender<T,R>) -> Optional<R>) {
    this.sortedBy {
        it as SendOrder
        it.order()
    }
    for(sender: Sender<T,R> in this) {
        try{
            val result: Optional<R> = action(sender)
            if(result.isPresent) {
                break
            } else {
                continue
            }
        } catch (e: RuntimeException) {
            e.printStackTrace()
            continue
        }

    }
}


fun <T, R> List<Sender<T, R>>.select(predicate: (Sender<T, R>) -> Boolean): List<Sender<T, R>> {
    return this.filter(predicate)
}

fun <T,R> MutableList<in Sender<T,R>>.register(sender:Sender<T,R>){
    this.add(sender)
}
