fun main() {
    val a = readln().toBigInteger()
    val b = readln().toBigInteger()
    println(a * b / a.gcd(b))
}
