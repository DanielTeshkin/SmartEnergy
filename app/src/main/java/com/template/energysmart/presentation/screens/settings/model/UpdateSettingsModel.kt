package com.template.energysmart.presentation.screens.settings.model

import com.template.energysmart.domain.model.PhaseControl
import com.template.energysmart.domain.model.VoltageControl

data class UpdateSettingsModel(
    val voltageControl: Boolean,
    val ecoMode: Boolean,
    val phaseControl: Boolean,
    val notifyEnabled: Boolean,
    val preventiveMode: Boolean,
    val voltageLow: Double,
    val voltageHigh: Double,
    val timeLow: Int,
    val timeHigh: Int,
    val timeWork: Int,
    val timeStop: Int,
    val timePause: Int,
    val timeWorkPreventive: Int,
    val timeBeforeWorkPreventive: Int,
    val generalOdometr: Int,
    val odometerChangeOil: Int,
    val value: Int

)
