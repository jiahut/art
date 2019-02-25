package link

import java.util.*

data class UserInfo(val userName: String,
                    val orgName: String,
                    val userEmail: String)

enum class UserEventType {
    USERADD,
    USERDELETE
}

class UserEvent(val type: UserEventType) : Observable() {

    var lastUserInfo: UserInfo? = null

    fun trigger(userInfo: UserInfo) {
        if(userInfo != lastUserInfo) {
            this.setChanged()
            this.notifyObservers(userInfo)
        }
        lastUserInfo = userInfo
    }

}

class LogObserver : Observer {
    override fun update(evt: Observable?, data: Any?) {
        evt as UserEvent
        println("fire the event = ${evt.type}, with data : ${data}")
    }
}

class MailObserver: Observer {

    override fun update(evt: Observable?, data: Any?) {
        evt as UserEvent
        println("send mail... ")
    }

}

fun main(args: Array<String>) {

    val userAddEvent = UserEvent(UserEventType.USERADD)

    userAddEvent.addObserver(LogObserver())

    val userInfo = UserInfo("jazz", "tech", "jiahut@gmail.com")
    userAddEvent.trigger(userInfo)
    val userInfo2 = UserInfo("jazz", "tech", "jiahut@gmail.com")
    // nothing happen
    userAddEvent.trigger(userInfo2)

    val userInfo3 = UserInfo("baby", "design", "baby@163.com")
    userAddEvent.addObserver(MailObserver())

    userAddEvent.trigger(userInfo3)
}