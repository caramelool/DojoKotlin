package com.lcmobile.kotlinbestpractices

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Transformations
import com.lcmobile.kotlinbestpractices.app.MagicNumber
import com.lcmobile.kotlinbestpractices.app.MainRepository
import com.lcmobile.kotlinbestpractices.mvvm.*


class MainViewModel(
    private val repository: MainRepository
) : AbstractViewModel() {

    private val loadingLiveData = liveData<MainViewState.Loading>()
    private val numberLiveData = liveData<MainViewState.MagicNumberState>()

    private val stringLiveData = liveData<MainViewState.StringState>()

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


//    fun request() {
//        loadingLiveData.postValue(MainViewState.Loading.Show)
//
//        val liveData = Transformations.map(repository.request()) {
//            MainViewState.StringState(it).also {
//                loadingLiveData.postValue(MainViewState.Loading.Hide)
//            }
//        }
//
//
//        mediator.addSource(liveData) {
//            mediator.value = it
//        }
//    }

    var i = 0

    fun request() {
        i++
        loadingLiveData.postValue(MainViewState.Loading.Show)
        repository.request(i % 2 == 0) {
            when (val response = it) {
                is MainRepository.RepositoryResult.Success -> {
                    val state = MainViewState.StringState(response.value)
                    stringLiveData.postValue(state)
                }
                is MainRepository.RepositoryResult.Error -> {
                    val state = MainViewState.StringState(response.exception.toString())
                    stringLiveData.postValue(state)
                }
            }
            loadingLiveData.postValue(MainViewState.Loading.Hide)
        }
    }

}

sealed class MainViewState : ViewState {

    sealed class Loading : MainViewState() {
        object Show : Loading()
        object Hide : Loading()
    }

    data class StringState(val message: String) : MainViewState()

    data class MagicNumberState(val list: List<MagicNumber>) : MainViewState()
}
