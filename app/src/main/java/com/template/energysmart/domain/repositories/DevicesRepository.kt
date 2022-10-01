package com.template.energysmart.domain.repositories

import com.template.energysmart.data.remote.api.model.request.BindDeviceData
import com.template.energysmart.data.remote.api.model.request.Command
import com.template.energysmart.data.remote.api.model.request.CommandRequest
import com.template.energysmart.data.remote.api.model.response.Device
import com.template.energysmart.data.remote.api.model.response.Metric
import com.template.energysmart.data.remote.api.model.response.Status
import kotlinx.coroutines.flow.Flow

interface DevicesRepository {
    fun getDevices() : Flow<List<Device>>
   suspend fun getDevice():Device
    suspend fun getMetrics():Metric
    fun sendCommand(command:Command):Flow<Status>
    fun bindDevice(data: BindDeviceData):Flow<Status>
    fun saveDevice(id:String)
   fun updateMode(command: Command):Flow<Status>
   fun getSavedDevice():String

}