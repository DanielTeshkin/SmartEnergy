package com.template.energysmart.domain.model
sealed class DeviceState{
    data class ControlState(val energyControlModel: EnergyControlModel):DeviceState()
    data class NotificationsState(val notificationModel: List<NotificationModel>):DeviceState()
}