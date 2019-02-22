package channel

import java.util.*

class MailSender : Sender<String, String>, SendOrder {

    override fun send(mail: String):Optional<String> {
        println("[MailSender] send $mail")
        // return  Optional.empty()
        return Optional.of("ok")
    }

    override fun type(): String {
        return "mail"
    }

    override fun order() = 1

}
