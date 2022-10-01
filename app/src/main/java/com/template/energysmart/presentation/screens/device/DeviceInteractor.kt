package com.template.energysmart.presentation.screens.device

import com.template.energysmart.data.remote.api.model.request.BindDeviceData
import com.template.energysmart.data.remote.api.model.response.Device
import com.template.energysmart.data.remote.api.model.response.Status
import com.template.energysmart.domain.repositories.DevicesRepository
import com.template.energysmart.presentation.base.BaseInteractor
import com.template.energysmart.presentation.state.ResponseState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeviceInteractor @Inject constructor(private val repository: DevicesRepository):BaseInteractor() {
    private val _state=MutableSharedFlow<DeviceViewState>()
    val data=_state.asSharedFlow()
    private val _response= MutableSharedFlow<ResponseState<Status>>()
    val response=_response.asSharedFlow()

    fun getDevices(){
        repository.getDevices().handleResult({ emit(_state,DeviceViewState.Loading(it))
            },{ devices->
                 when(devices.isNotEmpty()){
                     true->{
                         repository.saveDevice(devices[0].id)
                         emit(_state,DeviceViewState.IsThereBindDevice(true))
                     }
                     false-> emit(_state,DeviceViewState.IsThereBindDevice(false))
                 }
              },{
                emit(_state,DeviceViewState.Error(it))
            } )}

    fun bindDevice(uid:String,password:String){
        repository.bindDevice(BindDeviceData(uid,password)).handleResult(_response,{
            repository.saveDevice(uid)
        })
    }

}