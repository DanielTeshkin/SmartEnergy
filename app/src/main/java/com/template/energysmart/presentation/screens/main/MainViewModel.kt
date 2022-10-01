package com.template.energysmart.presentation.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.energysmart.R
import com.template.energysmart.domain.model.NotificationModel
import com.template.energysmart.presentation.base.BaseViewModel

import com.template.energysmart.presentation.screens.main.models.MainUiController
import com.template.energysmart.presentation.screens.main.models.MainViewState
import com.template.energysmart.presentation.screens.notifications.AlertNotificationState
import com.template.energysmart.presentation.screens.notifications.NotificationViewState
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
    private val _notification= MutableStateFlow(NotificationViewState())
    val notification=_notification.asStateFlow()
    private val _alertDialog= MutableStateFlow(AlertNotificationState())
    val alertDialog=_alertDialog.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    private val _error = MutableStateFlow("")
    val error=_error.asStateFlow()
    private val _imageMode = MutableStateFlow(R.drawable.ic_start_test)
    val imageMode=_imageMode.asStateFlow()

    init {
        mainInteractor.apply {
            scope = viewModelScope
             viewModelScope.repeat(15000){getCurrentSystemState()}
            subscribe(state) {
                when (it) {
                    is MainState.Loading -> _loading.value = it.isLoading
                    is MainState.Error -> _error.value = it.throwable.localizedMessage as String
                    is MainState.DataState -> _configuration.value = uiController.reduceState(it)
                    is MainState.NotificationsState -> _notification.value=it.notification
                    is MainState.AlertNotification -> _alertDialog.value=it.alertNotificationState
                }
            }
        }
    }


    fun handleEvent(event: MainViewEvent) = when (event) {
        is MainViewEvent.AutoModeEvent -> {
            _configuration.value.autoButton=R.drawable.auto_button
            _configuration.value.autoButtonIsEnabled = true
            _configuration.value.handButtonIsEnabled = false
            mainInteractor.updateMode("AUTO")
        }
        is MainViewEvent.ManualModeEvent -> {
            _configuration.value.manualButton=R.drawable.hand_button_green
            _configuration.value.autoButtonIsEnabled = false
            _configuration.value.handButtonIsEnabled = true
            mainInteractor.updateMode("MANUAL")
        }
        is MainViewEvent.StartGeneratorEvent -> {
            _configuration.value.commandButton=R.drawable.ic_stop_test
            mainInteractor.sendCommand("START")
        }
        is MainViewEvent.StopGeneratorEvent -> {
            _configuration.value.commandButton=R.drawable.ic_start_test
            mainInteractor.sendCommand("STOP")
        }
        is MainViewEvent.CloseAlertEvent -> {
            _alertDialog.value= AlertNotificationState()
            mainInteractor.closeAlert(event.id)
        }

    }
}










