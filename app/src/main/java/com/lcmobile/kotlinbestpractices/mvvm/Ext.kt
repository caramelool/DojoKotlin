package com.lcmobile.kotlinbestpractices.mvvm

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * Extension to create viewModel for Activities
 */
inline fun <reified T : ViewModel> AppCompatActivity.viewModel(
    factory: ViewModelProvider.Factory? = null
): Lazy<T> {
    return lazy {
        ViewModelProviders.of(this, factory)
            .get(T::class.java)
    }
}

/**
 * Extension to create viewModel for Fragments
 */
inline fun <reified T : ViewModel> Fragment.viewModel(
    factory: ViewModelProvider.Factory? = null
): Lazy<T> {
    return lazy {
        ViewModelProviders.of(this, factory)
            .get(T::class.java)
    }
}

/**
 * Extension to create a factory for viewModel
 */
inline fun <VM: ViewModel> viewModelFactory(
    crossinline block: () -> VM
) = object : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return block() as T
    }
}