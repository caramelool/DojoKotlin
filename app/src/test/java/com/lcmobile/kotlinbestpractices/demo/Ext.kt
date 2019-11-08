package com.lcmobile.kotlinbestpractices.demo

import org.mockito.ArgumentCaptor
import org.mockito.Mockito

inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

inline fun<reified T> argumentCaptor(
    block: ArgumentCaptor<T>.() -> Unit
) = ArgumentCaptor.forClass(T::class.java).run(block)