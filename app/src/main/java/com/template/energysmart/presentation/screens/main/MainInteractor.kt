package com.template.energysmart.presentation.screens.main

import com.template.energysmart.data.remote.api.model.request.Command
import com.template.energysmart.data.remote.api.model.response.Parameter

import com.template.energysmart.domain.GeneratorUseCase
import com.template.energysmart.domain.NotificationUseCase
import com.template.energysmart.domain.SettingsUseCase
import com.template.energysmart.domain.model.GeneratorDataModel
import com.template.energysmart.domain.model.Mode
import com.template.energysmart.presentation.base.BaseInteractor
import com.template.energysmart.presentation.screens.main.models.MainViewState


import com.template.energysmart.presentation.state.MainState

import kotlinx.coroutines.flow.*

import javax.inject.Inject

class MainInteractor @Inject constructor(private val devicesUseCase:GeneratorUseCase,
                                         private val notificationUseCase: NotificationUseCase,
                                         private val settingsUseCase: SettingsUseCase
                                         ):BaseInteractor() {

    private val _state= MutableSharedFlow<MainState>()
    val state=_state.asSharedFlow()

   init {
     reduce()
   }




    fun sendCommand(command:String)= devicesUseCase.sendCommand( Command.valueOf(command))
    fun update(parameter: Parameter)=settingsUseCase.update(parameter =parameter)


    fun reduce(){
        devicesUseCase.invoke().handleResult({ isLoading->emit(_state,MainState.Loading(isLoading))},
            { data->
                val state=MainViewState(
                   phase_vol_1 =data.metric.voltage_1,
                    phase_vol_2 = data.metric.voltage_2,
                    phase_vol_3 = data.metric.voltage_3,
                    temperature = data.metric.temperature,
                    isAuto = data.mode== Mode.AUTO,
                )
                emit(_state,MainState.DataState(data))
            },
            { error-> emit(_state,MainState.Error(error))

            }
            )
    }

    fun transformToViewState(data: GeneratorDataModel) {

    }


}



