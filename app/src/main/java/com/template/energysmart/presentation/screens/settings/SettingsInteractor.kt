package com.template.energysmart.presentation.screens.settings

import com.template.energysmart.data.remote.api.model.request.Command
import com.template.energysmart.data.remote.api.model.response.Device
import com.template.energysmart.data.remote.api.model.response.User
import com.template.energysmart.domain.GeneratorUseCase
import com.template.energysmart.presentation.base.BaseInteractor
import com.template.energysmart.presentation.screens.settings.model.SettingsPresentationState
import com.template.energysmart.presentation.screens.settings.model.UpdateSettingsModel
import com.template.energysmart.presentation.state.ResponseState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingsInteractor @Inject constructor(private val useCase: GeneratorUseCase):BaseInteractor() {
    val state= MutableSharedFlow<SettingsPresentationState>()
    val device= MutableSharedFlow<ResponseState<Device>>()
    val users= MutableStateFlow(listOf<User>())

    fun handleState(){
        useCase.invokeSettings().handleResult({
            //emit(state, SettingsPresentationState(loading = it))
        },{
             emit(state, SettingsPresentationState(settingsModel = it))
        },{
           // emit(state,SettingsPresentationState(error =it.localizedMessage ))
        })
    }

    fun resetOdo(request:Command)=useCase.resetOdo(request).handleResult()
    fun update(model:UpdateSettingsModel)=useCase.updateParameter(model).handleResult()
     fun getDevice()=useCase.getDevice().handleResult(device,{
         users.value=it.users
     })
}