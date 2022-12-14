package com.template.energysmart.presentation.state

sealed class ResponseState <out T> {
    data class Loading(val isLoading: Boolean) : ResponseState<Nothing>()
    data class Error(val throwable: Throwable) : ResponseState<Nothing>()
    data class Success<T>(val item: T) : ResponseState<T>()

}
