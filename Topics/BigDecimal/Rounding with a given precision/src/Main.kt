import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
    val a = readln()
    val b = readln().toInt()
    val aa = a.toBigDecimal().setScale(b, RoundingMode.HALF_DOWN)
    println(aa)
}
