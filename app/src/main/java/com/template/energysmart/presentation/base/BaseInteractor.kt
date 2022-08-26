package com.template.energysmart.presentation.base

import com.template.energysmart.presentation.state.ResponseState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseInteractor {

    open var scope:CoroutineScope?=null
  protected fun <T> Flow<T>.handleResult(
   flow: MutableStateFlow<ResponseState<T>>,
   onSuccess: ((T) -> Unit)? = null,
   onError: ((Throwable) -> Unit)? = null
  ) {
      scope?.let {
          onStart { flow.emit(ResponseState.Loading(true)) }
              .onEach {
                  flow.emit(ResponseState.Success(it))
                  onSuccess?.invoke(it)
              }
              .catch {
                  flow.emit(ResponseState.Error(it))
                  onError?.invoke(it)
              }
              .onCompletion { flow.emit(ResponseState.Loading(false)) }
              .launchIn(it)
      }
  }

  protected fun <T> Flow<T>.handleResult(
   flow: MutableSharedFlow<ResponseState<T>>,
   onSuccess: ((T) -> Unit)? = null,
   onError: ((Throwable) -> Unit)? = null
  ) {
      scope?.let {
          onStart { flow.emit(ResponseState.Loading(true)) }
              .onEach {
                  flow.emit(ResponseState.Success(it))
                  onSuccess?.invoke(it)
              }
              .catch {
                  flow.emit(ResponseState.Error(it))
                  onError?.invoke(it)
              }
              .onCompletion { flow.emit(ResponseState.Loading(false)) }
              .launchIn(it)
      }
  }

  protected fun <T> Flow<T>.handleResult(
   onLoading: ((Boolean) -> Unit)? = null,
   onSuccess: ((T) -> Unit)? = null,
   onError: ((Throwable) -> Unit)? = null
  ) {
      scope?.let { scope->
          onStart { onLoading?.invoke(true) }
              .onEach {
                  onSuccess?.invoke(it)
              }
              .catch {
                  onError?.invoke(it)
              }
              .onCompletion { onLoading?.invoke(false) }
              .launchIn(scope)
      }
  }

  protected fun <T> emit(flow: MutableStateFlow<T>, value: T) {
  scope?.launch {
    flow.emit(value)
   }
  }

  protected fun <T> emit(flow: MutableSharedFlow<T>, value: T) {
   scope?.launch {
    flow.emit(value)
   }
  }
}