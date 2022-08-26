package com.template.energysmart.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.template.energysmart.presentation.screens.main.models.MainUiController
import com.template.energysmart.presentation.screens.main.models.MainViewState
import com.template.energysmart.presentation.state.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(private val mainInteractor: MainInteractor):ViewModel() {

    private val uiController=MainUiController()
    private val configuration=MutableStateFlow(MainViewState())
    val send= MutableStateFlow(false)

    init {
        mainInteractor.scope=viewModelScope
        subscribe()
        viewModelScope.launch {
            mainInteractor.state.collect {
                if (it is MainState.DataState) {

                }
            }
        }
    }
    fun sendEvent(event:MainViewEvent){
        when(event){
            is MainViewEvent.SendCommandIntent->sendCommand()
            is MainViewEvent.ChangeModeIntent ->{}
        //updateParameter()
        }

    }


    private fun sendCommand(){
        //if(configuration.value.mode!="STOP")mainInteractor.sendCommand("START")
        //else mainInteractor.sendCommand("STOP")
    }





    private fun subscribe(){
       viewModelScope.repeat(30000){
        mainInteractor.reduce()
       }
    }



}
fun CoroutineScope.repeat(repeatMillis: Long, action: suspend () -> Unit) = this.launch {
    withContext(Dispatchers.IO) {
        while (isActive) {
            action()
            delay(repeatMillis)
        }
    }
}