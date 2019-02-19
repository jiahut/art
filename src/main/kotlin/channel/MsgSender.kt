package channel

import java.util.*

class MsgSender : Sender<String, String> {

    override fun send(msg: String): Optional<String> {
        println("[MsgSender] send $msg")
        return Optional.empty()
    }

    override fun type(): String {
        return "msg"
    }

}
