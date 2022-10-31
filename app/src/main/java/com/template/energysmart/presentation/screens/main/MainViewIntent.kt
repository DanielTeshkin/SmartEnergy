package com.template.energysmart.presentation.screens.main



sealed class MainViewEvent {
    object ModeEvent:MainViewEvent()
    object GeneratorCommandEvent:MainViewEvent()
    data class CloseAlertEvent(val id:String):MainViewEvent()
}