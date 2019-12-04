package com.lcmobile.kotlinbestpractices.mvvm

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class AbstractViewModel : ViewModel(), LifecycleObserver {

    val liveDataList = mutableListOf<LiveData<*>>()

    val mediator = MediatorLiveData<ViewState>()

    fun restoreMediator() {
        liveDataList.mapNotNull { it.value }
            .filterIsInstance<ViewState>()
            .forEach { mediator.value = it }
    }

    private val job = Job()
    val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}

fun <T : ViewState> AbstractViewModel.liveData(default: T? = null) =
    MutableLiveData<T>()
        .also {
            mediator.addSource(it) { value ->
                mediator.value = value
            }
        }
        .also { liveData ->
            default?.let { liveData.postValue(it) }
        }
        .also {
            liveDataList.add(it)
        }

fun AbstractViewModel.launch(
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(block = block)