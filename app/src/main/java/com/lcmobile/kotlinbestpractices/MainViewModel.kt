package com.lcmobile.kotlinbestpractices

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.lcmobile.kotlinbestpractices.app.MagicNumber
import com.lcmobile.kotlinbestpractices.app.MainRepository
import com.lcmobile.kotlinbestpractices.mvvm.*


class MainViewModel(
    private val repository: MainRepository
) : AbstractViewModel() {

    private val loadingLiveData = liveData<MainViewState.Loading>()
    private val numberLiveData = liveData<MainViewState.MagicNumberState>()

    private val numberList = mutableListOf<MagicNumber>()

    companion object {
        val FACTORY = viewModelFactory {
            val repository = MainRepository()
            MainViewModel(repository)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        loadingLiveData.postValue(MainViewState.Loading.Hide)
    }

    fun generateMagicNumber() {
        launch {
            loadingLiveData.postValue(MainViewState.Loading.Show)

            val number = repository.generateNumber()
            numberList.add(number)

            val state = MainViewState.MagicNumberState(numberList)
            numberLiveData.postValue(state)

            loadingLiveData.postValue(MainViewState.Loading.Hide)
        }
    }

}

sealed class MainViewState : ViewState {

    sealed class Loading : MainViewState() {
        object Show : Loading()
        object Hide : Loading()
    }

    data class MagicNumberState(val list: List<MagicNumber>) : MainViewState()
}
