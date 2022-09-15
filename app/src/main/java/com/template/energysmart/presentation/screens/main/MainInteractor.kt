package com.template.energysmart.presentation.screens.main

import android.util.Log
import com.template.energysmart.data.remote.api.model.request.Command
import com.template.energysmart.data.remote.api.model.request.ParameterRequest
import com.template.energysmart.data.remote.api.model.response.Notification
import com.template.energysmart.data.remote.api.model.response.Parameter

import com.template.energysmart.domain.GeneratorUseCase
import com.template.energysmart.domain.NotificationUseCase
import com.template.energysmart.domain.SettingsUseCase
import com.template.energysmart.domain.model.*
import com.template.energysmart.presentation.base.BaseInteractor
import com.template.energysmart.presentation.data.NotificationsResource
import com.template.energysmart.presentation.screens.main.models.MainViewState
import com.template.energysmart.presentation.screens.notifications.NotificationViewState


import com.template.energysmart.presentation.state.MainState

import kotlinx.coroutines.flow.*

import javax.inject.Inject

class MainInteractor @Inject constructor(private val devicesUseCase:GeneratorUseCase,
                                         private val notificationUseCase: NotificationUseCase,
                                         private val settingsUseCase: SettingsUseCase
                                         ):BaseInteractor() {

    private val _state= MutableSharedFlow<MainState>()
    val state=_state.asSharedFlow()
    fun sendCommand(command:String)= devicesUseCase.sendCommand( Command.valueOf(command)).handleResult()
    fun updateMode(command:String)= devicesUseCase.updateMode( Command.valueOf(command)).handleResult()



    fun getCurrentSystemState() {
        devicesUseCase.invoke()
            .handleResult({ isLoading ->
                Log.i("error",isLoading.toString())
                emit(_state, MainState.Loading(isLoading))
                          },
                { data ->
                    when(data){

                        is DeviceState.ControlState -> {
                            Log.i("data",data.energyControlModel.metric.time_work_timer)
                            emit(_state, MainState.DataState(data.energyControlModel))
                        }

                    }

                },
                { error ->
                    Log.i("error",error.message.toString())
                    emit(_state, MainState.Error(error))

                }
            )
    }



    fun transformToViewState(model: NotificationModel) :NotificationViewState{
        val resource=NotificationsResource()
        return NotificationViewState(
            image = resource.imageMap[model.data.image],
            title = model.data.title,
            description = model.data.description,
            instruction = model.data.instruction,
            theme = resource.themeMap[model.data.format]
        )

    }


}



