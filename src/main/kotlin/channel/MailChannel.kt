package channel

import java.util.*

class MailChannel : Channel<String, String>, ChannelOrder {

    override fun send(mail: String):Optional<String> {
        println("[MailChannel] send $mail")
        // return  Optional.empty()
        return Optional.of("ok")
    }

    override fun type(): String {
        return "mail"
    }

    override fun order() = 1

}
