package com.template.energysmart.data.repositories

import com.template.energysmart.data.local.LocalDataSource
import com.template.energysmart.data.remote.RemoteDataSource
import com.template.energysmart.data.remote.api.model.request.BindDeviceData
import com.template.energysmart.data.remote.api.model.request.Command
import com.template.energysmart.data.remote.api.model.request.CommandRequest
import com.template.energysmart.data.remote.api.model.request.ModeRequest
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
    override suspend fun getDevice() = handleOrDefault(Device()){remote.getDevice(local.getDeviceId())}

    override suspend fun getMetrics() = handleOrDefault(Metric()){remote.getMetrics(local.getDeviceId())}

   override fun sendCommand(command:Command)=flow {
       val result= handleOrDefault(Status()) {
           remote.sendCommand(CommandRequest(local.getDeviceId(), command)) }
          emit(result)
   }


    override fun resetOdo(command: Command)=flow {
        val result=handleOrDefault(Status()) { remote.resetOdo(CommandRequest(local.getDeviceId(), command)) }
        emit(result)
    }


    override fun bindDevice(data: BindDeviceData)=flow{
        val result=handleOrDefault(Status()){remote.bindDevice(data)}
        local.saveDevice(data.id)
        emit(result)
    }

    override fun saveDevice(id: String) =local.saveDevice(id)

    override fun updateMode(command: Command) = flow {
        val result = handleOrDefault(Status()) {
            remote.sendMode(
                ModeRequest(
                    local.getDeviceId(), command
                )
            )
        }
        emit(result)

    }

    override fun getSavedDevice(): String=local.getDeviceId()
    override fun unbind(id: String)=flow{
        val result=handle {  remote.unbind(local.getDeviceId(),id)}
        emit(result)
    }

    override fun exit()=local.clear()

}