package channel

import java.util.*

class MsgSender : Sender<String, String>, SendOrder {

    override fun send(msg: String): Optional<String> {
        println("[MsgSender] send $msg")
        return Optional.empty()
    }

    override fun type(): String {
        return "msg"
    }

    override fun order() = 0

}
