package com.template.energysmart.presentation.screens.settings

import com.template.energysmart.domain.GeneratorUseCase
import com.template.energysmart.presentation.base.BaseInteractor
import com.template.energysmart.presentation.screens.settings.model.SettingsPresentationState
import com.template.energysmart.presentation.screens.settings.model.UpdateSettingsModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class SettingsInteractor @Inject constructor(private val useCase: GeneratorUseCase):BaseInteractor() {
    val state= MutableSharedFlow<SettingsPresentationState>()

    fun handleState(){
        useCase.invokeSettings().handleResult({
            //emit(state, SettingsPresentationState(loading = it))
        },{
             emit(state, SettingsPresentationState(settingsModel = it))
        },{
           // emit(state,SettingsPresentationState(error =it.localizedMessage ))
        })
    }


    fun update(model:UpdateSettingsModel)=useCase.updateParameter(model).handleResult()
}