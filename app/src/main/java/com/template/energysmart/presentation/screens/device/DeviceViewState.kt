package com.template.energysmart.presentation.screens.device

import com.template.energysmart.data.remote.api.model.response.Device

sealed class DeviceViewState{
    data class IsThereBindDevice(val exist:Boolean):DeviceViewState()
    data class BindDevice(val uid:String="",val password:String="",val enabled:Boolean=false):DeviceViewState()
    data class Loading(val loading:Boolean):DeviceViewState()
    data class Error(val throwable: Throwable):DeviceViewState()
}
