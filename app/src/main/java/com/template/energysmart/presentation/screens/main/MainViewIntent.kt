package com.template.energysmart.presentation.screens.main



sealed class MainViewEvent {
    data class ChangeModeIntent(val checked: Boolean):MainViewEvent()
   object SendCommandIntent:MainViewEvent()
}