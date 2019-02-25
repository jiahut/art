package channel

import java.util.*

interface Channel<T,R> {
    fun send(msg: T): Optional<R>
    fun type():String
}

interface ChannelOrder {
    fun order(): Int
}
