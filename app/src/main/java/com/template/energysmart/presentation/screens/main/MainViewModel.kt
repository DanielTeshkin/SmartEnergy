package com.template.energysmart.presentation.screens.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.template.energysmart.presentation.base.BaseViewModel

import com.template.energysmart.presentation.screens.main.models.MainUiController
import com.template.energysmart.presentation.screens.main.models.MainViewState
import com.template.energysmart.presentation.screens.notifications.AlertNotificationState
import com.template.energysmart.presentation.screens.notifications.NotificationViewState
import com.template.energysmart.presentation.state.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(private val mainInteractor: MainInteractor): BaseViewModel() {
     val uiController = MainUiController(mainInteractor)
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
    private val initSystem= MutableStateFlow(false)
    init {
        mainInteractor.apply {
            scope = viewModelScope
               viewModelScope.repeat(15000){ getCurrentSystemState() }
            subscribe(state) {
                when (it) {
                    is MainState.Loading -> _loading.value = it.isLoading
                    is MainState.Error -> _error.value = it.throwable.message as String
                    is MainState.DataState -> uiController.reduceState(it)
                    is MainState.NotificationsState -> _notification.value=it.notification
                    is MainState.AlertNotification -> uiController._alertDialog.value=it.alertNotificationState
                }
            }
            uiController.apply {
                subscribe(currentState) {
                    Log.i("change",it.autoButton.toString())
                    _configuration.value = it
                        //  if (!initSystem.value)initSystem.value=true
                }

                }
            }
        }



    fun handleEvent(event: MainViewEvent) =uiController.handleEvent(event)


}










