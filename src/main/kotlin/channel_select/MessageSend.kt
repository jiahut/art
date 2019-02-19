package channel_select

interface MessageSend {
    fun sendMailMessage(message:String):String

    fun sendLiveMessage(message:Int):Int
}