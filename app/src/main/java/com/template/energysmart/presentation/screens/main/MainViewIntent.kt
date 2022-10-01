package com.template.energysmart.presentation.screens.main



sealed class MainViewEvent {
    object AutoModeEvent:MainViewEvent()
    object ManualModeEvent:MainViewEvent()
    object StartGeneratorEvent:MainViewEvent()
    object StopGeneratorEvent:MainViewEvent()
    data class CloseAlertEvent(val id:String):MainViewEvent()
}