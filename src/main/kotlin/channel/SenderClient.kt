package channel

fun main(args: Array<String>) {
    val senders = SenderManager.buildSenderList<String, String>()
    senders.register(MailSender())
    senders.register(MsgSender())

    senders.select {
        it.type().length > 1
    }.sendAll {
        it.send("verification code 100")
    }


    println("-------------------------------")
    val senders2 = SenderManager.buildSenderList<String, String>()
    senders2.register(MailSender())
    senders2.register(MsgSender())

    senders2.select {
        it.type().length > 1
    }.sendByOrder {
        it.send("verification code 110")
    }



    println("-------------------------------")
    val senders3 = SenderManager.buildSenderList<String, String>()
    senders3.register(MailSender())
    senders3.register(MsgSender())

    senders3.select {
        it.type().length > 1
    }.sendTillSuccess{
        it.send("verification code 120")
    }
}

