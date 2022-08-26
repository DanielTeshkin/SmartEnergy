package com.template.energysmart.domain.mappers

import com.template.energysmart.data.remote.api.model.response.Metric
import com.template.energysmart.data.remote.api.model.response.Parameter
import com.template.energysmart.domain.model.*
import javax.inject.Inject

class Mapper @Inject constructor() {
    fun mapMetric(result: Metric)= MetricModel(phase_first = result.input1,
        phase_second = result.input2,
        phase_third = result.input3,
        mode = result.mode,
        command = result.cmd,
        temperature = result.temperature_air,
        oil_level = result.fuel,
        time = result.toil
    )


    fun map(model:Metric,parameter: Parameter): EnergyControlModel {
        val source=model.cmd
        val systemStateController=SystemStateController(model.input1,model.input2,model.input3,model.gin.toInt(),
            source, phaseControl = if(parameter.phasescount) 0 else parameter.phasecontrol
            )


           val metrics= if(source=="STOP")
               GeneralMetric(voltage_1 =model.input1,
               voltage_2 = model.input2,
               voltage_3 = model.input3,
               temperature = model.temperature_air,
               oil_level = model.fuel,
               time_to_change_oil = model.toil,
               time_work_timer = model.ttb
           ) else  GeneralMetric(voltage_1 =model.gin.toInt(),
               voltage_2 = model.gin.toInt(),
               voltage_3 = model.gin.toInt(),
               temperature = model.temperature_air,
               oil_level = model.fuel,
               time_to_change_oil = model.toil,
               time_work_timer = model.ttb)
        val generalState=systemStateController.getGeneralState()
        return EnergyControlModel(
            generalState = generalState,
            metric = metrics,
            notifications = listOf(),
            eco_control = if(model.mode=="AUTO")   EcoControl(
                time_pause = parameter.pause_before_starting_the_generator,
                time_stop = parameter.downtime,
                time_work = parameter.eco_generator_run_time
            ) else null,
            power_source = PowerSource.valueOf(source),
            mode = Mode.valueOf(model.mode)
        )


    }

    fun mapParameter(parameter: Parameter)= SettingsModel(
        voltage_control =
        when (parameter.voltage_control) {
            true -> VoltageControl(
                time_high = parameter.ext_voltage_time_phase_high,
                time_low = parameter.ext_voltage_time_phase_low,
                voltage_high = parameter.ext_voltage_phase_high,
                voltage_low = parameter.ext_voltage_phase_low
            )
            false -> null
        },
        eco_control =
        when(parameter.ecoEnable) {
            true ->
                EcoControl(
                    time_pause = parameter.pause_before_starting_the_generator,
                    time_stop = parameter.downtime,
                    time_work = parameter.eco_generator_run_time
                )
            false -> null
        },
        preventiveStart = when(parameter.fuel) {
            true->  PreventiveMode(
                time_work = parameter.prof_generator_run_time,
                time_before_start = parameter.start_time
            )
            false -> null
        },
        phaseControl = when(parameter.phasescount){
            true-> PhaseControl(phase_count_control = parameter.phasecontrol)
            false -> null
        },
        balance = parameter.blns.toString(),
        phone = parameter.phone,
        version = parameter.fmw.toString(),
        energy = parameter.ti.toString(),
        level_oil = parameter.pow.toString(),
        general_odometr = parameter.toil.toString(),
        odometr_before_change_oil = parameter.reset_oil_change.toString(),
        temperatureAir = parameter.odo.toString(),
        temperatureGenerator = parameter.activation_temperature.toString(),
        notifyEnabled = parameter.notifDisabled



    )

    fun mapNotification(code:Int): DataNotification {
        return  when(code){
            1-> DataNotification(
                format = FormatNotification.SEND_COMMAND_NOTIFY,
                title ="Внимание",
                description = "С города выключили электрчество"
            )
            2-> DataNotification(
                format = FormatNotification.SEND_COMMAND_PROBLEM,
                title = "Ошибка запуска",
                description = "Генератор не завелся,\n" +
                        "Неизвестная ошибка"
            )
            3-> DataNotification(
                format = FormatNotification.SEND_COMMAND_PROBLEM,
                title = "Холодно",
                description = "Генератор не завелся,\n" +
                        "Низкая температура воздуха"
            )
            4-> DataNotification(
                format = FormatNotification.SEND_COMMAND_ERROR,
                title = "Эххх. Ничего не получается!",
                description = "Прийдется запускать в ручную\n" +
                        "Посмотрите инструкцию как это сдеать"
            )
            5-> DataNotification(
                format = FormatNotification.SEND_COMMAND_ERROR,
                title = "Эххх. Ничего не получается!",
                description = "Прийдется запускать в ручную\n" +
                        "Посмотрите инструкцию как это сдеать"
            )
            else->{
                DataNotification()
            }
        }
    }




}