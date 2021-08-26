package com.nikodem.crypto.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<STATE : ViewState>(
    private val initialState: STATE
) : ViewModel() {
    private val _viewState: MutableLiveData<STATE> = MutableLiveData(initialState)
    val viewState: LiveData<STATE> = _viewState

    val isInitialState: Boolean
        get() = _viewState.value === initialState
    val currentState: STATE
        get() = viewState.value!!

    val showToastEvent = LiveEvent<String>()
    val showSnackbarEvent = LiveEvent<String>()

    protected fun updateViewState(update: (STATE) -> STATE) {
        val newState = update(_viewState.value!!)
        if (newState != _viewState.value!!) {
            _viewState.value = newState
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception, "CoroutineExceptionHandler")
        handleError(exception)
    }

    open fun handleError(exception: Throwable) {}

    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler, block = block)
    }
}

fun LiveEvent<Unit>.fireEvent() {
    value = Unit
}

fun <T> LiveEvent<T>.fireEvent(t: T) {
    value = t
}
