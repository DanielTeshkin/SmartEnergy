package com.template.energysmart.presentation.screens.main

import com.template.energysmart.data.remote.api.model.response.Parameter

sealed class MainViewAction{
    data class NavigateToSettings(val parameter: Parameter):MainViewAction()
    object NavigateToInstruction:MainViewAction()
}
