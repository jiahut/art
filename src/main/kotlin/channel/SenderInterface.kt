package channel

import java.util.*

interface Sender<T,R> {
    fun send(msg: T): Optional<R>
    fun type():String
}

interface SendOrder {
    fun order(): Int
}
