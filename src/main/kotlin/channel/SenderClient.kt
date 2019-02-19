package channel

fun main(args: Array<String>) {
    val senders = SenderManager.createSenders<String,String>()
    senders.register(MailSender())
    senders.register(MsgSender())

    senders
      .select {
        it.type().length > 1}
      .send {
        it.send("verification code 044166")
      }
    }
