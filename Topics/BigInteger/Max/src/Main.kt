import java.math.BigInteger
import kotlin.math.abs

fun main() {
    val a = readln().toBigInteger()
    val b = readln().toBigInteger()
    val d = a + b + (a - b).abs()
    val c = BigInteger.TWO
    println(d / c)

}
