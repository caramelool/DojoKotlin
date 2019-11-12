package com.lcmobile.kotlinbestpractices.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
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


    sealed class RepositoryResult<out T> {
        data class Success<out T>(val value: T) : RepositoryResult<T>()
        data class Error(val exception: Throwable) : RepositoryResult<Nothing>()
    }

    fun request(valid: Boolean, block: (RepositoryResult<String>) -> Unit)  {
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            if (valid) {
                block(RepositoryResult.Success("Hola"))
            } else {
                block(RepositoryResult.Error(IllegalArgumentException()))
            }

        }
    }

}