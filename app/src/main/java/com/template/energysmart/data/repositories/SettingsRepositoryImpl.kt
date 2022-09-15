package com.template.energysmart.data.repositories

import com.template.energysmart.data.local.LocalDataSource
import com.template.energysmart.data.remote.RemoteDataSource
import com.template.energysmart.data.remote.api.model.request.ParameterRequest
import com.template.energysmart.data.remote.api.model.response.Parameter
import com.template.energysmart.domain.repositories.SettingsRepository

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val remote: RemoteDataSource,
                                                  private val local: LocalDataSource) :BaseRepository(),SettingsRepository {


    private val device_id=local.getDeviceId()
  override  suspend  fun getParameter()=handleOrDefault(Parameter()){remote.getParameters(device_id)}
    override fun getCurrentParameter()= flow {
        val data=local.getParameter()
        if (data!=null) emit(data)
        val result=getParameter()
        emit(result)
        local.saveParameter(result)
    }

    override fun updateParameter( parameter: ParameterRequest)= flow {
        val result =
            handleOrDefault(Parameter()) { remote.changeParameters(device_id, parameter) }
        emit(result)
    }

}