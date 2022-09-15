package com.template.energysmart.domain

import com.template.energysmart.data.remote.api.model.request.ParameterRequest
import com.template.energysmart.data.remote.api.model.response.Parameter
import com.template.energysmart.domain.repositories.SettingsRepository
import javax.inject.Inject

class SettingsUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
   suspend fun invoke()=settingsRepository.getParameter()
    fun update(parameter: ParameterRequest)=settingsRepository.updateParameter(parameter)
}