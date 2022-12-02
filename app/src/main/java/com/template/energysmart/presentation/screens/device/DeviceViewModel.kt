package com.template.energysmart.presentation.screens.device

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.template.energysmart.presentation.base.BaseViewModel
import com.template.energysmart.presentation.base.UiController
import com.template.energysmart.presentation.screens.device.components.ListDevicesScreen
import com.template.energysmart.presentation.screens.main.models.MainViewState
import com.template.energysmart.presentation.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class DeviceViewModel @Inject constructor(private val interactor: DeviceInteractor): BaseViewModel(){

    private val _loading=MutableStateFlow(false)
    val loading=_loading.asStateFlow()
    private val _error= MutableStateFlow("")
    val error=_error.asStateFlow()
    private  val _navigation= MutableSharedFlow<Boolean>()
    val navigation=_navigation.asSharedFlow()
   private val _loadScreen= MutableStateFlow(false)
    val loadScreen=_loadScreen.asStateFlow()
    private val _state= MutableStateFlow(DeviceViewState.BindDevice())
    val state=_state.asStateFlow()
    init {
        interactor.apply {
            scope = viewModelScope
            getDevices()
            subscribe(data){
                when(it){
                    is DeviceViewState.Loading -> _loading.value=it.loading
                    is DeviceViewState.Error ->{
                        _error.value=it.throwable.message?:""
                        _loading.value=false

                    }
                    is DeviceViewState.IsThereBindDevice ->{
                        _navigation.emit(it.exist)
                        _loadScreen.value=it.exist
                    }
                    else -> {}
                }
            }
        }
    }
    private fun updateUID(text:String){
        if (text.isEmpty()) _state.value=DeviceViewState.BindDevice(uid=text, password = state.value.password, enabled = false)
        else _state.value=DeviceViewState.BindDevice(uid = text, password = state.value.password,enabled = state.value.password.isNotEmpty())

    }

    private fun updatePassword(text:String){
        if (text.isEmpty()) _state.value=DeviceViewState.BindDevice(uid=state.value.uid, password = text, enabled = false)
        else _state.value=DeviceViewState.BindDevice(uid = state.value.uid, password =text,enabled = state.value.uid.isNotEmpty())
    }


    private fun bindDevice() {
         interactor.bindDevice(state.value.uid,state.value.password)
         start(viewModelScope) {
             interactor.response.collect {
                 Log.i("mg","mgg")
                 when (it) {
                     is ResponseState.Loading -> _loading.value = true


                     is ResponseState.Success -> _navigation.emit(true)
                     is ResponseState.Error -> _error.value = it.throwable.message ?: ""
                 }
             }
         }
     }

    fun reduceEvent(event: DeviceViewEvent){
        when(event){
            is DeviceViewEvent.BindDeviceEvent -> {
                bindDevice()
                DeviceViewState.BindDevice(uid = state.value.uid, password =state.value.password,enabled = false)
            }

            is DeviceViewEvent.ChangeTextUIDEvent-> updateUID(event.text)
            is DeviceViewEvent.ChangeTextPasswordEvent-> {
                DeviceViewState.BindDevice(uid = state.value.uid, password =state.value.password,enabled = true)
                updatePassword(event.text)
            }
            }
        }
    }

