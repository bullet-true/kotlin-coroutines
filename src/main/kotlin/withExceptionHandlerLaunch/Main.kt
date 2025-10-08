package withExceptionHandlerLaunch

import kotlinx.coroutines.*
import java.math.BigInteger

/**
 * Реализация с помощью билдера launch и exceptionHandler
 * **/

val jobLaunch = SupervisorJob()
val exceptionHandlerLaunch = CoroutineExceptionHandler { _, throwable ->
    println("\nToo long - error from handler: ${throwable.message}")
}
val scopeLaunch = CoroutineScope(jobLaunch + exceptionHandlerLaunch + Dispatchers.Default)

fun main(): Unit = runBlocking {
    var numb1: BigInteger? = null
    var numb2: BigInteger? = null
    var numb3: BigInteger? = null

    val progressJobEH = launch {
        while (isActive) {
            print(". ")
            delay(100)
        }
    }

    scopeLaunch.launch {
        try {
            println("Starting Coroutine 1")
            numb1 = FibonacciLaunch.take(500_000)
        } catch (e: TimeoutCancellationException) {
            throw RuntimeException("Coroutine 1 timed out", e)
        }
    }

    scopeLaunch.launch {
        try {
            println("Starting Coroutine 2")
            numb2 = FibonacciLaunch.take(600_000)
        } catch (e: TimeoutCancellationException) {
            throw RuntimeException("Coroutine 2 timed out", e)
        }
    }

    scopeLaunch.launch {
        try {
        println("Starting Coroutine 3")
        numb3 = FibonacciLaunch.take(100_000)
        } catch (e: TimeoutCancellationException) {
            throw RuntimeException("Coroutine 3 timed out", e)
        }
    }

    jobLaunch.complete()
    jobLaunch.join()
    progressJobEH.cancelAndJoin()

    println()
    println("The result of the first coroutine: ${numb1 ?: "Calculation takes too long time"}")
    println("------------------------------------------------------------------------------")
    println("The result of the second coroutine: ${numb2 ?: "Calculation takes too long time"}")
    println("------------------------------------------------------------------------------")
    println("The result of the third coroutine: ${numb3 ?: "Calculation takes too long time"}")
}