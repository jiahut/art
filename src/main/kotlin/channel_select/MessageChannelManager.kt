package channel_select

import java.lang.Exception

class MessageChannelManager {
    fun <T, R> send(params: T, action: (MessageSend, T) -> R): R {
        val messageServices = mutableListOf<MessageSend>()
        messageServices.add(MessageSendChannelA())
        messageServices.add(MessageSendChannelB())

        while (messageServices.isNotEmpty()) {
            val messageSender = messageServices[0]
            try {
                return action.invoke(messageSender, params)
            } catch (e: Exception) {
                println("${messageSender.javaClass.name} is fail")
                messageServices.remove(messageSender)
                if (messageServices.isEmpty()) {
                    break
                }
            }
        }
        throw Exception("所有渠道都失败")
    }
}