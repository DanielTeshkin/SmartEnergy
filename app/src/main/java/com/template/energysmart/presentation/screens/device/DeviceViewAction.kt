package com.template.energysmart.presentation.screens.device

sealed class DeviceViewAction {
    object Nothing:DeviceViewAction()
    object NavigationToMainScreen:DeviceViewAction()
}