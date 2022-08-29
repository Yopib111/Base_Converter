package converter

import java.math.BigInteger

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
            if (numberInBase == "/back") break
            if (numberInBase == "/exit") break@loop
            println("Conversion result: ${conv(sourceBase.toInt(), targetBase.toInt(), numberInBase)}")
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

        return result.reversed()
    }
