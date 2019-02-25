package channel

import java.lang.RuntimeException
import java.util.*

// typealias SimpleSender = Channel<String, String>

object SenderManager {

    fun <T, R> buildSenderList(): MutableList<Channel<T, R>> {
        return mutableListOf<Channel<T, R>>();
    }

}


fun <T, R> List<Channel<T,R>>.sendAll(action: (Channel<T, R>) -> Optional<R>) {
    this.forEach {
        action(it)
    }
}


fun <T, R> List<out Channel<T,R>>.sendByOrder(action: (Channel<T,R>) -> Optional<R>) {
    this.sortedBy {
        it as ChannelOrder
        it.order()
    }.forEach {
        action(it)
    }
}


fun <T, R> List<out Channel<T,R>>.sendTillSuccess(action: (Channel<T,R>) -> Optional<R>) {
    this.sortedBy {
        it as ChannelOrder
        it.order()
    }
    for(Channel: Channel<T,R> in this) {
        try{
            val result: Optional<R> = action(Channel)
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


fun <T, R> List<Channel<T, R>>.select(predicate: (Channel<T, R>) -> Boolean): List<Channel<T, R>> {
    return this.filter(predicate)
}

fun <T,R> MutableList<in Channel<T,R>>.register(Channel:Channel<T,R>){
    this.add(Channel)
}
