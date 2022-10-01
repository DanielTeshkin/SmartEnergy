package com.template.energysmart.presentation.base

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.energysmart.presentation.state.ResponseState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

typealias Loader =(Boolean) -> Unit
typealias Success<T> = (T) -> Unit
typealias Error=(String) -> Unit
abstract class BaseViewModel :ViewModel() {
   protected fun CoroutineScope.repeat(repeatMillis: Long, action: suspend () -> Unit) = this.launch {
        withContext(Dispatchers.IO) {
            while (isActive) {
                action()
                delay(repeatMillis)
            }
        }
    }

    protected fun <T> handleState(state:ResponseState<T>,loader: Loader,success: Success<T>,error: Error){
        when(state){
            is ResponseState.Loading -> loader.invoke(state.isLoading)
            is ResponseState.Success -> success.invoke(state.item)
            is ResponseState.Error -> error.invoke(state.throwable.message?:"")
        }


    }
    protected fun start(scope: CoroutineScope,action:suspend ()->Unit){
        scope.launch(Dispatchers.IO) {
            action.invoke()
        }
    }

    protected fun <T> subscribe(flow: Flow<T>, action: suspend (T) -> Unit) {
        viewModelScope.launch {
            while (isActive) {
                flow.collect{
                    action.invoke(it)
                }
            }
        }
    }


}