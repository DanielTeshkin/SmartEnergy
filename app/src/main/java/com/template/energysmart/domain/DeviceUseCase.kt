package com.template.energysmart.domain

import com.template.energysmart.data.remote.api.model.request.Command
import com.template.energysmart.data.remote.api.model.response.Parameter
import com.template.energysmart.domain.mappers.Mapper
import com.template.energysmart.domain.model.GeneratorDataModel
import com.template.energysmart.domain.model.NotificationModel
import com.template.energysmart.domain.model.SettingsModel
import com.template.energysmart.domain.repositories.DevicesRepository
import com.template.energysmart.domain.repositories.NotificationsRepository
import com.template.energysmart.domain.repositories.SettingsRepository
import com.template.energysmart.presentation.screens.settings.model.UpdateSettingsModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GeneratorUseCase @Inject constructor(private val devicesRepository: DevicesRepository,
                                           private val settingsRepository: SettingsRepository,
                                           private val notificationsRepository: NotificationsRepository,
                                           private val mapper: Mapper) {



   fun sendCommand(command: Command)= devicesRepository.sendCommand(command)

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
        val metrics=getMetrics()
        val model=mapper.map(metrics,result)
        emit(model)

    }

    fun updateParameter(parameter: UpdateSettingsModel)=settingsRepository.updateParameter(
        Parameter(
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
            reset_oil_change = parameter.odometerChangeOil,
            toil = parameter.generalOdometr.toDouble()

        )
    )







}