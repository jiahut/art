package channel

import java.util.*

class MsgChannel : Channel<String, String>, ChannelOrder {

    override fun send(msg: String): Optional<String> {
        println("[MsgChannel] send $msg")
        return Optional.empty()
    }

    override fun type(): String {
        return "msg"
    }

    override fun order() = 0

}
