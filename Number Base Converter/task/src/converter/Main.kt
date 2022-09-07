package converter

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

fun main() {
    var readInputFirst = ""

loop@   while (readInputFirst != "/exit") {
        var numberInBase = ""
        print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ")
        readInputFirst = readln()
        if (readInputFirst == "/exit") break
        val (sourceBase, targetBase) = readInputFirst.split(' ')
        while (numberInBase != "/back") {
            print("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back) ")
            numberInBase = readln()
            val numberInBaseAfterDot = numberInBase.substringAfter('.')
            val numberInBaseBeforeDot = numberInBase.substringBefore('.')
            if (numberInBase == "/back") break
            if (numberInBase == "/exit") break@loop
            if ("." !in numberInBase) {
                println("Conversion result: ${conv(sourceBase.toInt(), targetBase.toInt(), numberInBase)}")
            } else if (numberInBase[numberInBase.length-1] =='0' && numberInBase[numberInBase.length-2] =='.') {
                println("Conversion result: ${conv(sourceBase.toInt(), targetBase.toInt(), numberInBaseBeforeDot)}.00000")
            } else {

                println("Conversion result: ${conv(sourceBase.toInt(), targetBase.toInt(), numberInBaseBeforeDot)}.${convAfterDot(sourceBase.toInt(), targetBase.toInt(), numberInBaseAfterDot)}")

            }
        }
    }
}

fun conv(sourceBase: Int, targetBase: Int, numberInBase: String): String {
        var check = 0
        val alfabith = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var result = ""
        var summa = BigInteger.ZERO
        var num = sourceBase.toBigInteger()
        val ten = BigInteger.TEN

        for (i in numberInBase.uppercase().reversed()) {
            if (i == numberInBase.uppercase().last() && check == 0) {
                when (i) {
                    in alfabith -> {
                        summa += alfabith.indexOf(i).toBigInteger() + ten
                        check = 1
                    }
                    in '0'..'9' -> {
                        summa += i.digitToInt().toBigInteger()
                        check = 1
                    }
                }
            } else {
                when (i) {
                    in alfabith -> {
                        summa += (alfabith.indexOf(i).toBigInteger() + ten) * num
                        num = num * sourceBase.toBigInteger()
                    }
                    in '0'..'9' -> {
                        summa += i.digitToInt().toBigInteger() * num
                        num = num * sourceBase.toBigInteger()
                    }
                }

            }
        }
        var targetBaseChanger = targetBase
        if (targetBase <= 9) {
            targetBaseChanger = targetBase
        } else targetBaseChanger = 10

        while (summa >= (targetBaseChanger - 1).toBigInteger()) {
            when (targetBase) {
                in 10..36 -> {
                    val newSumma: BigInteger = (summa - summa / targetBase.toBigInteger() * targetBase.toBigInteger())
                    if (newSumma >= ten) {
                        result += alfabith[(summa - summa / targetBase.toBigInteger() * targetBase.toBigInteger()).toInt() - 10]
                        summa = summa / targetBase.toBigInteger()
                    } else {
                        result += newSumma.toString()
                        summa = summa / targetBase.toBigInteger()
                    }
                }
                in 2..9 -> {
                    result += summa - summa / targetBase.toBigInteger() * targetBase.toBigInteger()
                    summa = summa / targetBase.toBigInteger()
                }
            }
        }
        if (summa.toInt() != 0) result += summa.toString()
        if (result == "") result = "0"
        return result.reversed()
    }

fun convAfterDot (sourceBase: Int, targetBase: Int, numberInBase: String) : String {
    val alfabith = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    var summa = BigDecimal.ZERO.setScale(10)
    var newBase = BigDecimal.ONE.setScale(10)
    val ten = BigDecimal.TEN.setScale(10)

//сначала преобразовываем в десятиричную сисмету
    for (i in numberInBase.uppercase()) {
        if (i in alfabith) {
            summa += (alfabith.indexOf(i).toBigDecimal().setScale(5) + ten) / (sourceBase.toBigDecimal().setScale(10) * newBase)
            newBase = newBase * sourceBase.toBigDecimal()
// выше просто продублировал, нужно будет удалить эти две строчки выше

        } else {
            summa += i.digitToInt().toBigDecimal().setScale(10) / (sourceBase.toBigDecimal().setScale(10) * newBase)
            newBase *= sourceBase.toBigDecimal()
        }
    }

    var summaAfterDot = ""
    var temporary = summa.setScale(10)
    val zero = BigDecimal.ZERO.setScale(10)

    while ((temporary - temporary.toBigInteger().toBigDecimal()) != zero) {
        temporary = (temporary * targetBase.toBigDecimal()).setScale(10)
        val integerPart = temporary.toBigInteger()
        temporary = temporary - integerPart.toBigDecimal()
        if (integerPart >= ten.toBigInteger()) {
            summaAfterDot += alfabith[integerPart.toInt() - 10]
        } else summaAfterDot += integerPart.toString()
        if (summaAfterDot.length == 5) break
    }
    while (summaAfterDot.length < 5) {
        summaAfterDot += "0"
    }

    return summaAfterDot
}
