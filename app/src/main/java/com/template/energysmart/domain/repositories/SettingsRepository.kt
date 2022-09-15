package com.template.energysmart.domain.repositories

import com.template.energysmart.data.remote.api.model.request.ParameterRequest
import com.template.energysmart.data.remote.api.model.response.Parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface SettingsRepository {


   suspend fun getParameter():Parameter
   fun getCurrentParameter():Flow<Parameter>
    fun updateParameter( parameter: ParameterRequest): Flow<Parameter>
}