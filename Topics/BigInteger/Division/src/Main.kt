fun main() {
    val a = readln().toBigInteger()
    val b = readln().toBigInteger()
    val (result, remainder) = a.divideAndRemainder(b)
    println("$a = $b * $result + $remainder")
}
