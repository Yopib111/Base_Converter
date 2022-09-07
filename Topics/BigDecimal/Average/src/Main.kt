import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
    val a = BigDecimal(readln())
    val b = BigDecimal(readln())
    val c = BigDecimal(readln())
    val d = BigDecimal(3)
    println(((a + b + c) / d).setScale(0, RoundingMode.DOWN))
}