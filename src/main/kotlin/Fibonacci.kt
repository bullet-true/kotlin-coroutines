import kotlinx.coroutines.*
import java.math.BigInteger

object Fibonacci {
    suspend fun take(index: Int): BigInteger {
        if (index == 0) {
            return 0.toBigInteger()
        }

        var num0 = 0.toBigInteger()
        var num1 = 1.toBigInteger()
        var i = 2
        withTimeout(4_000) {
            try {
                while (i <= index) {
                    yield()
                    val num2 = num0 + num1
                    num0 = num1
                    num1 = num2
                    i++
                }
            } catch (e: CancellationException) {
                println("\nException: too long: ${e.message}")
            }
        }
        return num1
    }
}



