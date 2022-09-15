package com.template.energysmart.presentation.screens.settings

sealed class SettingsViewEvent{
    data class SwitchStateChangeEvent(val state:Boolean,val parameter:ParameterType):SettingsViewEvent()
    data class ValueChangerEvent(val input:String,val parameter: ParameterValueType):SettingsViewEvent()
    data class СhoicePhaseEvent(val state:Boolean,val number:Int):SettingsViewEvent()
    object ResetOdometrEvent :SettingsViewEvent()
    object ResetOdometrToChangeOilEvent :SettingsViewEvent()
}
enum class ParameterType {
    ECO,
    PREVENTIVE,
    VOLTAGE_CONTROL,
    NOTIFY_ENABLED,
    PHASE_CONTROL
}
enum class ParameterValueType{
    VOLTAGE_LOW,
    VOLTAGE_HIGH,
    TIME_LOW,
    TIME_HIGH,
    TIME_PAUSE,
    TIME_WORK,
    TIME_STOP,
    TIME_BEFORE_START,
    TIME_PREVENTIVE_WORK
}