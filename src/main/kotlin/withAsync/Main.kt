package withAsync

import kotlinx.coroutines.*
import java.math.BigInteger

/**
 * Реализация с помощью билдера async и exceptionHandler
 * **/

val jobAsync = Job()
val exceptionHandlerAsync = CoroutineExceptionHandler { _, throwable ->
    println("\nToo long - error from handler: ${throwable.message}")
}
val scopeAsync = CoroutineScope(jobAsync + exceptionHandlerAsync + Dispatchers.Default)

suspend fun main() {
    var n1: BigInteger? = null
    var n2: BigInteger? = null
    var n3: BigInteger? = null

    scopeAsync.launch {

        val progressJobAsync = launch {
            while (isActive) {
                print(". ")
                delay(100)
            }
        }

        val num1 = async {
            try {
                FibonacciAsync.take(400_000)
            } catch (e: TimeoutCancellationException) {
                throw RuntimeException("Coroutine 1 timed out", e)
            }
        }

        val num2 = async {
            try {
                println("Starting Coroutine 2")
                FibonacciAsync.take(500_000)
            } catch (e: TimeoutCancellationException) {
                throw RuntimeException("Coroutine 2 timed out", e)
            }
        }

        val num3 = async {
            try {
                println("Starting Coroutine 3")
                FibonacciAsync.take(600_000)
            } catch (e: TimeoutCancellationException) {
                throw RuntimeException("Coroutine 3 timed out", e)
            }
        }

        n1 = num1.await()
        n2 = num2.await()
        n3 = num3.await()

        progressJobAsync.cancelAndJoin()

    }

    jobAsync.complete()
    jobAsync.join()

    println()
    println("The result of the first coroutine: ${n1 ?: "Calculation takes too long time"}")
    println("------------------------------------------------------------------------------")
    println("The result of the second coroutine: ${n2 ?: "Calculation takes too long time"}")
    println("------------------------------------------------------------------------------")
    println("The result of the third coroutine: ${n3 ?: "Calculation takes too long time"}")
}