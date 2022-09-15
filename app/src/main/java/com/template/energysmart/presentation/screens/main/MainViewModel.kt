package com.template.energysmart.presentation.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.energysmart.presentation.base.BaseViewModel

import com.template.energysmart.presentation.screens.main.models.MainUiController
import com.template.energysmart.presentation.screens.main.models.MainViewState
import com.template.energysmart.presentation.state.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(private val mainInteractor: MainInteractor): BaseViewModel() {

    private val uiController = MainUiController()
    private val _configuration = MutableStateFlow(MainViewState())
    val configuration=_configuration.asStateFlow()
    private val loading = MutableStateFlow(false)
    private val error = MutableStateFlow("")

    init {
        mainInteractor.apply {
            scope = viewModelScope
             viewModelScope.repeat(15000){getCurrentSystemState()}
            subscribe(state) {
                when (it) {
                    is MainState.Loading -> loading.value = it.isLoading
                    is MainState.Error -> error.value = it.throwable.localizedMessage as String
                    is MainState.DataState -> {
                        //Log.i("abc","fff")
                        _configuration.value = uiController.reduceState(it)
                    }
                    is MainState.NotificationsState -> Log.i("fff","fff")
                }
            }
        }
    }


    fun handleEvent(event: MainViewEvent) = when (event) {
        is MainViewEvent.AutoModeEvent -> mainInteractor.updateMode("AUTO")
        is MainViewEvent.ManualModeEvent -> mainInteractor.updateMode("MANUAL")
        is MainViewEvent.StartGeneratorEvent -> mainInteractor.sendCommand("START")
        is MainViewEvent.StopGeneratorEvent -> mainInteractor.sendCommand("STOP")
    }
}










