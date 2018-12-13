import android.util.Log
import com.tencent.bugly.proguard.x


fun main(args: Array<String>) {
    var nums = 1..100
    var result = 0
    for (num in nums) {
        result = result + num
    }
    Log.e("jjzhang", "结果：${result}")
}

var numi = { num1: Int, num2: Int -> num1 + num2 }

var numj: (Int, Int) -> Int = { x, y -> x + y }

fun add(param1: Int, param2: Int) = ""