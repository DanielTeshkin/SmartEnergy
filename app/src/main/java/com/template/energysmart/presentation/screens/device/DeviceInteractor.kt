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
    private val _response= MutableSharedFlow<ResponseState<Status>>()
    val response=_response.asSharedFlow()


   fun check(){
        repository.getDevices().handleResult({ emit(_state,DeviceViewState.Loading(it))
            },{ devices->
             //emit(_state,DeviceViewState.BindDevice)


            },{
                emit(_state,DeviceViewState.Error(it))
            }

                ) }

    fun setState()=_state

    fun bindDevice(uid:String,password:String){
        repository.bindDevice(BindDeviceData(uid,password)).handleResult(_response,{
            repository.saveDevice(uid)
        })
    }

}