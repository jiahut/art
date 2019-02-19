package channel_select

import java.lang.Exception

class MessageSendChannelA : MessageSend {

    override fun sendMailMessage(message: String):String {
        throw Exception("fail")
    }

    override fun sendLiveMessage(message: Int):Int {
        println("Channel A send live message")
        return message
    }

}