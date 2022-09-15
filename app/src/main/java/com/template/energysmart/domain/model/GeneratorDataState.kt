package com.template.energysmart.domain.model

sealed class GeneratorDataState{
    data class EnergyControlDataState(val item:EnergyControlModel):GeneratorDataState()
    data class NotificationsDataState(val items:NotificationsDataState):GeneratorDataState()
}

