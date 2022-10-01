package com.template.energysmart.presentation.screens.main


import com.template.energysmart.R
import com.template.energysmart.data.remote.api.model.request.Command
import com.template.energysmart.domain.GeneratorUseCase
import com.template.energysmart.domain.model.*
import com.template.energysmart.presentation.base.BaseInteractor
import com.template.energysmart.presentation.data.NotificationsResource
import com.template.energysmart.presentation.screens.notifications.AlertNotificationState
import com.template.energysmart.presentation.screens.notifications.NotificationViewState


import com.template.energysmart.presentation.state.MainState
import com.template.energysmart.presentation.theme.ErrorText
import com.template.energysmart.presentation.theme.Orange
import com.template.energysmart.presentation.theme.RedLight
import com.template.energysmart.presentation.theme.YellowLemon

import kotlinx.coroutines.flow.*

import javax.inject.Inject

class MainInteractor @Inject constructor(private val devicesUseCase:GeneratorUseCase):BaseInteractor() {

    private val _state= MutableSharedFlow<MainState>()
    val state=_state.asSharedFlow()
    fun sendCommand(command:String)= devicesUseCase.sendCommand( Command.valueOf(command)).handleResult()
    fun updateMode(command:String)= devicesUseCase.updateMode( Command.valueOf(command)).handleResult()
    fun closeAlert(id:String) = devicesUseCase.closeAlert(id).handleResult()
    fun getCurrentSystemState() {
        devicesUseCase.invoke()
            .handleResult({ isLoading ->
                emit(_state, MainState.Loading(isLoading))},
                { data ->
                    when(data){
                        is DeviceState.ControlState -> emit(_state, MainState.DataState(data.energyControlModel))
                        is DeviceState.NotificationsState ->{
                            val current=data.notificationModel.first()
                            when(current.data.format!=FormatNotification.ALERT_NOTIFY
                                    && current.data.format!=FormatNotification.ALERT_DANGER){
                                true -> emit(_state,MainState.NotificationsState(current.transformToViewState()))
                                false -> emit(_state,MainState.AlertNotification(current.transformToAlert()))
                            }
                        }
                    }

                },
                { error -> emit(_state, MainState.Error(error)) }
            )
    }



    private fun NotificationModel.transformToViewState() :NotificationViewState{
        val resource=NotificationsResource(this.id)
        val events=resource.eventMap[this.data.format]
        return NotificationViewState(
            image = resource.imageMap[this.data.image],
            title = this.data.title,
            description = this.data.description,
            instruction = this.data.instruction,
            theme = resource.themeMap[this.data.format],
            textMode=this.data.action,
            buttonFirstEvent = events?.first,
            buttonSecondEvent = events?.second)
    }
    private fun NotificationModel.transformToAlert():AlertNotificationState{
        return AlertNotificationState(
            id=this.id,
            background = if (this.data.format==FormatNotification.ALERT_DANGER) RedLight
                      else YellowLemon,
            lineColor =  if (this.data.format==FormatNotification.ALERT_DANGER) ErrorText
        else Orange,
            title = this.data.title,
            description = this.data.description,
            imageClose = if (this.data.format==FormatNotification.ALERT_DANGER) R.drawable.ic_cancel_alert_red
            else R.drawable.ic_cancel_alert_notify

        )
    }


}



