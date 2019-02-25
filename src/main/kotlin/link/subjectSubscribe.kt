package link

import java.util.concurrent.CopyOnWriteArrayList

interface Subscriber<T> {
    fun subscribe(data: T)
}

interface Subject<T> {
    fun attach(sub: Subscriber<T>)
    fun detach(sub: Subscriber<T>)
    fun notifyAll(data: T)
}


class School : Subject<Student> {
    val subList = CopyOnWriteArrayList<Subscriber<Student>>()

    override fun attach(sub: Subscriber<Student>) {
        println("attach")
        subList.add(sub)
    }

    override fun detach(sub: Subscriber<Student>) {
        subList.remove(sub)
    }

    override fun notifyAll(data: Student) {
        subList.forEach {
            it.subscribe(data)
        }
    }

}

fun School.attach(action: (Student) -> Unit) {
    this.subList.add(object : Subscriber<Student> {
        override fun subscribe(data: Student) {
            action(data)
        }
    })
}

data class Student(var userName: String, var userNo: Number)

fun main(args: Array<String>) {

    val school = School()
    school.attach {
        println(it)
    }

    school.notifyAll(Student("jazz", 110))

}
