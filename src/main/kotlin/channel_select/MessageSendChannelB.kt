package channel_select

class MessageSendChannelB : MessageSend {
    override fun sendMailMessage(message: String):String {
        println("Channel B send mail message")
        return message
    }

    override fun sendLiveMessage(message: Int):Int {
        println("Channel B send live message")
        return message
    }

}