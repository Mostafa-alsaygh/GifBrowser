package com.example.gifbrowserapp.presentation.features.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gifbrowserapp.data.errors.ConnectionException
import com.example.gifbrowserapp.data.errors.EmptyBodyException
import com.example.gifbrowserapp.data.errors.InternetDisconnectedException
import com.example.gifbrowserapp.data.errors.ResponseException
import com.example.gifbrowserapp.data.errors.TimeoutException
import com.example.gifbrowserapp.data.errors.UnAuthorizedException
import com.example.gifbrowserapp.data.errors.ValidationException
import com.example.gifbrowserapp.data.utils.getValueOf
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


abstract class BaseViewModel<S, E>(initState: S) : ViewModel() {

    private val _state = MutableStateFlow(initState)
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<E?>()
    val event = _event
    //todo use this bro

    protected fun <T> tryToExecute(
        block: suspend () -> T,
        onSuccess: (T) -> Unit = {},
        onError: (ErrorState) -> Unit = {},
        checkSuccess: (T) -> Boolean = { true },
        onCompleted: () -> Unit = {},
        inScope: CoroutineScope = viewModelScope
    ): Job {
        return inScope.launch(Dispatchers.IO) {
            runCatching { block() }
                .onSuccess { response ->
                    Timber.d(response.toString())
                    if (checkSuccess(response)) {
                        onSuccess(response)
                        return@onSuccess
                    }

                    onError(ErrorState.RequestFailed())
                }
                .onFailure { mapExceptionToErrorState(throwable = it, onError = onError) }
            onCompleted()
        }
    }

    protected fun <T> tryToCollect(
        block: suspend () -> Flow<T>,
        onNewValue: (T) -> Unit = {},
        onError: (ErrorState) -> Unit = {},
        onCompleted: () -> Unit = {},
        latest: Boolean = false,
        takeWhile: (T) -> Boolean = { true },
        inScope: CoroutineScope = viewModelScope
    ): Job {
        return inScope.launch(Dispatchers.IO) {
            runCatching {
                if (latest)
                    block()
                        .takeWhile(takeWhile)
                        .collectLatest(onNewValue)
                else
                    block()
                        .takeWhile(takeWhile)
                        .collect(onNewValue)
            }
                .onFailure { mapExceptionToErrorState(throwable = it, onError = onError) }
            onCompleted()
        }
    }


    protected fun updateState(notifyEvent: E? = null, updater: S.() -> S) {
        _state.update(updater)
        emitNewEvent(notifyEvent ?: return)
    }

    protected fun emitNewEvent(newEvent: E) {
        viewModelScope.launch(Dispatchers.IO) {
            _event.emit(newEvent)
        }
    }

    private inline fun <T> MutableStateFlow<T>.update(block: T.() -> T) {
        while (true) {
            val prevValue = value
            val nextValue = block(prevValue)
            if (compareAndSet(prevValue, nextValue))
                return
        }
    }

    private fun mapExceptionToErrorState(throwable: Throwable, onError: (ErrorState) -> Unit) {
        Timber.e(throwable)
        val message = throwable.message.getValueOf("message")

        val exception = when (throwable) {
            is ConnectException -> ConnectionException()
            is SocketTimeoutException,
            is TimeoutCancellationException -> TimeoutException()

            is UnknownHostException -> InternetDisconnectedException()
            else -> throwable
        }

        when (exception) {
            is UnAuthorizedException -> ErrorState.UnAuthorized(message)
            is ConnectionException -> ErrorState.NoInternet(exception.message)
            is InternetDisconnectedException -> ErrorState.NoInternet(exception.message)
            is EmptyBodyException -> ErrorState.EmptyBody(message)
            is TimeoutException -> ErrorState.Timeout(exception.message)
            is ValidationException -> ErrorState.Validation(exception.message)
            is ResponseException -> {
                when (exception.code) {
                    HttpStatus.SC_UNAUTHORIZED -> ErrorState.UnAuthorized(exception.message)
                    HttpStatusSC_TOO_MANY_REQUESTS -> ErrorState.RequestFailed(TOO_MANY_REQUESTS)
                    else -> ErrorState.RequestFailed(exception.message)
                }
            }

            else -> ErrorState.RequestFailed(message).also { Timber.e(throwable) }
        }.run(onError)
    }

    private companion object {
        const val TOO_MANY_REQUESTS = "Too Many Attempts"
        const val HttpStatusSC_TOO_MANY_REQUESTS = 429
    }
}
