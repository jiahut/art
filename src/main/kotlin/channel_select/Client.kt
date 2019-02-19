package channel_select

fun main(args: Array<String>) {
    val messageChannelManager = MessageChannelManager()
    val result = messageChannelManager.send(5) {
        messageSend, param ->  messageSend.sendLiveMessage(param)
    }
    println(result)

    println("-------------------------------------")

    val result2 = messageChannelManager.send("message") {
        messageSend, param ->  messageSend.sendMailMessage(param)
    }
    println(result2)
}