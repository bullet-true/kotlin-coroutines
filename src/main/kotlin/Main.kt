import kotlinx.coroutines.*
import java.math.BigInteger

/**
 * Реализация с помощью билдера launch и try-catch
 * **/

val job = Job()
val scope = CoroutineScope(job)

fun main(): Unit = runBlocking {
    var num1: BigInteger? = null
    var num2: BigInteger? = null
    var num3: BigInteger? = null
    var num4: BigInteger? = null
    var num5: BigInteger? = null
    var num6: BigInteger? = null

    val progressJob = launch {
        while (isActive) {
            print(". ")
            delay(100)
        }
    }

    scope.launch {
        println("Starting Coroutine 1")
        num1 = Fibonacci.take(400_000)
    }

    scope.launch {
        println("Starting Coroutine 2")
        num2 = Fibonacci.take(500_000)
    }

    scope.launch {
        println("Starting Coroutine 3")
        num3 = Fibonacci.take(600_000)
    }

    scope.launch {
        println("Starting Coroutine 4")
        num4 = Fibonacci.take(450_000)
    }

    scope.launch {
        println("Starting Coroutine 5")
        num5 = Fibonacci.take(350_000)
    }

    scope.launch {
        println("Starting Coroutine 6")
        num6 = Fibonacci.take(250_000)
    }

    job.complete()
    job.join()
    progressJob.cancelAndJoin()

    println()
    println("The result of the first coroutine: ${num1 ?: "Calculation takes too long time"}")
    println("------------------------------------------------------------------------------")
    println("The result of the second coroutine: ${num2 ?: "Calculation takes too long time"}")
    println("------------------------------------------------------------------------------")
    println("The result of the third coroutine: ${num3 ?: "Calculation takes too long time"}")
    println("------------------------------------------------------------------------------")
    println("The result of the fourth coroutine: ${num4 ?: "Calculation takes too long time"}")
    println("------------------------------------------------------------------------------")
    println("The result of the fifth coroutine: ${num5 ?: "Calculation takes too long time"}")
    println("------------------------------------------------------------------------------")
    println("The result of the sixth coroutine: ${num6 ?: "Calculation takes too long time"}")
    print("Completed")
}