package com.template.energysmart.presentation.screens.settings.model

import com.template.energysmart.domain.model.*

data class SettingsPresentationState(
    val loading:Boolean=false,
    val error:String="",
    val settingsModel: SettingsModel =SettingsModel()

    )
