package com.template.energysmart.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel :ViewModel() {
   private fun CoroutineScope.repeat(repeatMillis: Long, action: suspend () -> Unit) = this.launch {
        withContext(Dispatchers.IO) {
            while (isActive) {
                action()
                delay(repeatMillis)
            }
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
                delay(1000)
                flow.collect{
                    action.invoke(it)
                }
            }
        }
    }


}