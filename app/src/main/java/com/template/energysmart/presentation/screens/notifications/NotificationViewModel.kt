package com.template.energysmart.presentation.screens.notifications

import androidx.lifecycle.viewModelScope
import com.template.energysmart.domain.repositories.DevicesRepository
import com.template.energysmart.domain.repositories.NotificationsRepository
import com.template.energysmart.presentation.base.BaseViewModel
import com.template.energysmart.presentation.screens.main.MainInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor (private val interactor:NotificationInteractor ) : BaseViewModel() {
    private  val _instructionNavigation= MutableStateFlow(false)
    val instruction=_instructionNavigation.asStateFlow()
    private  val _mainNavigation= MutableSharedFlow<Boolean>()
    val mainNavigation=_mainNavigation.asSharedFlow()
    private val _isShowDialog= MutableStateFlow(false)
    val isShowDialog=_isShowDialog.asStateFlow()
   private val _errorShow= MutableStateFlow("")
    val errorShow=_errorShow.asStateFlow()

    init {
        interactor.apply {
            scope=viewModelScope
            subscribe(loading) {_isShowDialog.value=it}
            subscribe(error) {_errorShow.value=it}
            subscribe(status){_mainNavigation.emit(true)}
        }
    }



    fun handleEvent(event:NotificationsViewEvent){
        when(event){
            is NotificationsViewEvent.IgnoreNotificationEvent -> {
                viewModelScope.launch {
                    _mainNavigation.emit(true)
                }
            }
            is NotificationsViewEvent.NavigationToInstructionEvent -> _instructionNavigation.value=true
            is NotificationsViewEvent.ClickOnOkEvent -> interactor.clickOnOk(event.id)

        }
    }



}