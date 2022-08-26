package com.template.energysmart.presentation.screens.device

sealed class DeviceViewEvent {
    data class DeviceChoiceEvent(val id:String):DeviceViewEvent()
    object BindDeviceEvent : DeviceViewEvent()
    data class ChangeTextUIDEvent(val text:String):DeviceViewEvent()
    data class ChangeTextPasswordEvent(val text:String):DeviceViewEvent()
    object Nothing:DeviceViewEvent()

}