package concurrent

import java.util.*
import java.util.concurrent.*;

/**
 *  并发容器
 *  遍历的过程可被更新
 */
fun main(args: Array<String>) {
    var map = ConcurrentHashMap<String, String>()
//    var map = HashMap<String, String>()
    (1..1000).forEach {
        map["key$it"] = "value$it"
    }
    try {
        map.forEach { key: String, _: String ->
            if (key == "key3") {
                map["key3"] = "valuexxxxx"
            }
        }
    } catch (e: ConcurrentModificationException) {
        e.printStackTrace()
    }
    println(map)
    println(map["key3"])
}

