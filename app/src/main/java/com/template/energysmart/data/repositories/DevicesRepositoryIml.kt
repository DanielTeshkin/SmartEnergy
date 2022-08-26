package com.template.energysmart.data.repositories

import com.template.energysmart.data.local.LocalDataSource
import com.template.energysmart.data.remote.RemoteDataSource
import com.template.energysmart.data.remote.api.model.request.BindDeviceData
import com.template.energysmart.data.remote.api.model.request.Command
import com.template.energysmart.data.remote.api.model.request.CommandRequest
import com.template.energysmart.data.remote.api.model.response.Device
import com.template.energysmart.data.remote.api.model.response.Metric
import com.template.energysmart.data.remote.api.model.response.Status
import com.template.energysmart.domain.repositories.DevicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DevicesRepositoryIml @Inject constructor(private val remote: RemoteDataSource,private val local:LocalDataSource) :BaseRepository(),DevicesRepository {
    override fun getDevices() = flow {
        val result=handleOrEmptyList { remote.getDevices()  }
        emit(result)
    }
    override fun getDevice(): Flow<Device> = flow {

        val result=handleOrDefault(Device()){remote.getDevice(local.getDeviceId())}
        emit(result)
    }
    override suspend fun getMetrics() = handleOrDefault(Metric()){remote.getMetrics(local.getDeviceId())}

   override fun sendCommand(command:Command)=flow {

     val result=  handleOrDefault(Status()) { remote.sendCommand(CommandRequest(local.getDeviceId(),command)) }
          emit(result)
   }

    override fun bindDevice(data: BindDeviceData)=flow{
        val result=handleOrDefault(Status()){remote.bindDevice(data)}
        local.saveDevice(data.id)
        emit(result)
    }

    override fun saveDevice(id: String) =local.saveDevice(id)


}