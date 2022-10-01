package com.template.energysmart.presentation.state

import com.template.energysmart.data.remote.api.model.response.Metric
import com.template.energysmart.data.remote.api.model.response.Notification
import com.template.energysmart.data.remote.api.model.response.Parameter
import com.template.energysmart.domain.model.EnergyControlModel
import com.template.energysmart.domain.model.GeneratorDataModel
import com.template.energysmart.domain.model.MetricModel
import com.template.energysmart.domain.model.NotificationModel
import com.template.energysmart.presentation.screens.notifications.AlertNotificationState
import com.template.energysmart.presentation.screens.notifications.NotificationViewState

sealed class MainState {
    data class Loading(val isLoading: Boolean) : MainState()
    data class Error(val throwable: Throwable) : MainState()
    data class DataState(val data:EnergyControlModel): MainState()
    data class NotificationsState(val notification: NotificationViewState):MainState()
    data class AlertNotification(val alertNotificationState: AlertNotificationState):MainState()
}
data class DataMain(var metric: MetricModel?= MetricModel(),
                    var parameter: Parameter?= Parameter(),
                    var notification: List<NotificationModel>?= listOf())



