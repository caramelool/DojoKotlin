package com.lcmobile.kotlinbestpractices.app

import kotlinx.coroutines.delay
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainRepository {

    suspend fun generateNumber(): MagicNumber {
        delay(1000)
        return suspendCoroutine { suspend ->
            var size = 0
            var value = ""
            while (size < 6) {
                value = (1..6)
                    .map { random() }
                    .sorted()
                    .distinct()
                    .let {
                        size = it.size
                        it.joinToString(separator = " - ")
                    }
            }
            val number = MagicNumber(value)
            suspend.resume(number)
        }

    }

    private fun random() = (1 until 60).random()
            .toString()
            .padStart(2, '0')

}