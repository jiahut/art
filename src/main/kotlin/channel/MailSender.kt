package channel

import java.util.*

class MailSender : Sender<String, String> {

    override fun send(mail: String):Optional<String> {
        println("[MailSender] send $mail")
        return  Optional.empty()
    }

    override fun type(): String {
        return "mail"
    }

}
