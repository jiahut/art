package channel

fun main(args: Array<String>) {
    val senders = SenderManager.buildSenderList<String, String>()
    senders.register(MailChannel())
    senders.register(MsgChannel())

    senders.select {
        it.type().length > 1
    }.sendAll {
        it.send("verification code 100")
    }


    println("-------------------------------")
    val senders2 = SenderManager.buildSenderList<String, String>()
    senders2.register(MailChannel())
    senders2.register(MsgChannel())

    senders2.select {
        it.type().length > 1
    }.sendByOrder {
        it.send("verification code 110")
    }



    println("-------------------------------")
    val senders3 = SenderManager.buildSenderList<String, String>()
    senders3.register(MailChannel())
    senders3.register(MsgChannel())

    senders3.select {
        it.type().length > 1
    }.sendTillSuccess{
        it.send("verification code 120")
    }
}

