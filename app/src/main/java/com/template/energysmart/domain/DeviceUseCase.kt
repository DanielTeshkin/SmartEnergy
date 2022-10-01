package com.template.energysmart.domain

import com.template.energysmart.data.remote.api.model.request.Command
import com.template.energysmart.data.remote.api.model.request.ParameterRequest
import com.template.energysmart.domain.mappers.Mapper
import com.template.energysmart.domain.model.DeviceState
import com.template.energysmart.domain.model.EnergyControlModel
import com.template.energysmart.domain.model.NotificationModel
import com.template.energysmart.domain.repositories.DevicesRepository
import com.template.energysmart.domain.repositories.NotificationsRepository
import com.template.energysmart.domain.repositories.SettingsRepository
import com.template.energysmart.presentation.screens.settings.model.UpdateSettingsModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeneratorUseCase @Inject constructor(private val devicesRepository: DevicesRepository,
                                           private val settingsRepository: SettingsRepository,
                                           private val notificationsRepository: NotificationsRepository,
                                           private val mapper: Mapper) {



   fun sendCommand(command: Command)= devicesRepository.sendCommand(command)
    fun updateMode(command: Command)=devicesRepository.updateMode(command)
    fun closeAlert(id:String)=notificationsRepository.clickOnOk(id)

    private suspend fun notificationMap()=
        notificationsRepository.getNotificationList().map {
            val map= mapper.mapNotification(it.notification.code)
            NotificationModel(id=it.id,map)
        }
   private  suspend fun getMetrics() =devicesRepository.getMetrics()
    fun invokeSettings()=flow{
        val result=settingsRepository.getParameter()
        emit(mapper.mapParameter(result))
    }

    fun invoke()= flow {
        val result=settingsRepository.getParameter()
        val device=devicesRepository.getDevice()
        val notifications=notificationMap()
        if(notifications.isNotEmpty()) {
            emit(DeviceState.NotificationsState(notifications))
        }
        val metrics=getMetrics()
        val model=mapper.map(metrics,result,device)
        emit(DeviceState.ControlState(model))
    }

    fun updateParameter(parameter: UpdateSettingsModel)=settingsRepository.updateParameter(
        ParameterRequest(
            ecoEnable = parameter.ecoMode,
            voltage_control = parameter.voltageControl,
            phasescount = parameter.phaseControl,
            notifDisabled = parameter.notifyEnabled,
            fuel = parameter.preventiveMode,
            ext_voltage_phase_high = parameter.voltageHigh,
            ext_voltage_phase_low = parameter.voltageLow,
            ext_voltage_time_phase_high = parameter.timeHigh,
            ext_voltage_time_phase_low = parameter.timeLow,
            pause_before_starting_the_generator = parameter.timePause,
            downtime = parameter.timeStop,
            eco_generator_run_time = parameter.timeWork,
            prof_generator_run_time = parameter.timeWorkPreventive,
            start_time = parameter.timeBeforeWorkPreventive,
            reset_oil_change = parameter.odometerChangeOil.toDouble(),
            toil = parameter.generalOdometr.toDouble(),
            phasecontrol = parameter.value
        )
    )
}

