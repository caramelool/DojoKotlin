package com.lcmobile.kotlinbestpractices.mvvm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface ViewModelInterface<T : AbstractViewModel> {

    val viewModel: T

    fun onStateChanged(state: ViewState)

    fun setupViewModel(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(viewModel)
        val observer = Observer<ViewState> {
            it?.let(::onStateChanged)
        }
        viewModel.mediator.observe(lifecycleOwner, observer)
    }

}