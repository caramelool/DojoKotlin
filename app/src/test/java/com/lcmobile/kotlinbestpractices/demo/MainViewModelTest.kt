package com.lcmobile.kotlinbestpractices.demo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lcmobile.kotlinbestpractices.MainViewModel
import com.lcmobile.kotlinbestpractices.MainViewState
import com.lcmobile.kotlinbestpractices.app.MagicNumber
import com.lcmobile.kotlinbestpractices.app.MainRepository
import com.lcmobile.kotlinbestpractices.mvvm.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.Mockito.`when` as whenever
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = TestCoroutineRule()

    @Mock
    lateinit var repository: MainRepository

    @Mock
    lateinit var observer: Observer<ViewState>

    private lateinit var viewModel : MainViewModel

    @Before
    fun before() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `test generate magic number`() = runBlocking {

        val magicNumber : MagicNumber = mock()
        whenever(repository.generateNumber()).thenReturn(magicNumber)

        viewModel.mediator.observeForever(observer)

        viewModel.generateMagicNumber()

        argumentCaptor<ViewState> {

            verify(observer, times(3)).onChanged(capture())

            val (show, number, hide) = allValues

            val numberList = listOf(magicNumber)

            assertEquals(show, MainViewState.Loading.Show)
            assertEquals(number, MainViewState.MagicNumberState(numberList))
            assertEquals(hide, MainViewState.Loading.Hide)
        }
    }
}