package concurrent

import java.util.*
import kotlin.collections.HashMap

/**
 *  并发容器
 *  遍历的过程可被更新
 */
fun main(args: Array<String>) {
//    var map = ConcurrentHashMap<String, String>()
    var map = HashMap<String, String>()
    (1..10).forEach {
        map["key$it"] = "value$it"
    }
    try {
        map.forEach { key: String, _: String ->
            if (key == "key3") {
                map["key0"] = "value0"
            }
        }
    } catch (e: ConcurrentModificationException) {
        e.printStackTrace()
    }
    println(map)
}

private fun m(cmap: MutableMap<String, String>) {
}
