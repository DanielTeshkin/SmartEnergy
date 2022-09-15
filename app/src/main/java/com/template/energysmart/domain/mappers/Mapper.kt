package com.template.energysmart.domain.mappers

import com.template.energysmart.data.remote.api.model.response.Device
import com.template.energysmart.data.remote.api.model.response.Metric
import com.template.energysmart.data.remote.api.model.response.Parameter
import com.template.energysmart.domain.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun map(model: Metric, parameter: Parameter, device: Device): EnergyControlModel {
        val source=device.command
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

            eco_control = if(device.mode=="AUTO")   EcoControl(
                time_pause = parameter.pause_before_starting_the_generator,
                time_stop = parameter.downtime,
                time_work = parameter.eco_generator_run_time
            ) else null,
            power_source =if(source=="START") PowerSource.GENERATOR
            else PowerSource.NETWORK
            ,
            mode = Mode.valueOf(device.mode),

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
        general_odometr = parameter.toil.toInt(),
        odometr_before_change_oil = parameter.reset_oil_change.toInt(),
        temperatureAir = parameter.odo.toString(),
        temperatureGenerator = parameter.activation_temperature.toString(),
        notifyEnabled = parameter.notifDisabled
    )

    fun mapNotification(code:Int): DataNotification {
        val text="Обрыв фазы"
        val placeFirst="С города"
        val placeSecond="В доме"
        val low="Низкое напряжение"
        val high="Высокое напряжение"
        return when(code){
            1-> DataNotification(
                format = FormatNotification.SEND_COMMAND_NOTIFY,
                title ="Внимание",
                description = "С города выключили электрчество",
                image = ImageType.CITY_OFF
            )
            2-> DataNotification(
                format = FormatNotification.SEND_COMMAND_PROBLEM,
                title = "Ошибка запуска",
                description = "Генератор не завелся,\n" +
                        "Неизвестная ошибка",
                image = ImageType.LAUNCH_ERROR_GENERATOR
            )
            3-> DataNotification(
                format = FormatNotification.SEND_COMMAND_PROBLEM,
                title = "Холодно",
                description = "Генератор не завелся,\n" +
                        "Низкая температура воздуха",
                image = ImageType.LAUNCH_ERROR_COLD
            )
            4-> DataNotification(
                format = FormatNotification.SEND_COMMAND_ERROR,
                title = "Эххх. Ничего не получается!",
                description = "Прийдется запускать в ручную\n" +
                        "Посмотрите инструкцию как это сдеать",
                image = ImageType.LAUNCH_ERROR_GENERATOR
            )
            5-> DataNotification(
                format = FormatNotification.SEND_COMMAND_ERROR,
                title = "Эххх. Ничего не получается!",
                description = "Прийдется запускать в ручную\n" +
                        "Посмотрите инструкцию как это сдеать",
                image = ImageType.LAUNCH_ERROR_COLD
            )
            6->{
                DataNotification(
                    format = FormatNotification.SEND_COMMAND_PROBLEM,
                    title = "Перегрузка",
                    description = "Генератор не завелся. Перегрузка!!\n" +
                            "Выключайте энергоёмкие приборы\n",
                    image = ImageType.OVERLOAD
                )
            }

            7->{
                DataNotification(
                    format = FormatNotification.SEND_COMMAND_ERROR,
                    title = "Эххх. Ничего не получается!",
                    description = "Прийдется запускать в ручную\n" +
                            "Посмотрите инструкцию как это сдеать",
                    image = ImageType.OVERLOAD
                )
            }

            8->{
                DataNotification(
                    format = FormatNotification.INSTRUCTION_NOTIFY,
                    title = "Внимание",
                    description = "Генератор не завелся!\n" +
                            "На генераторе выбило автомат\n" +
                            "\n" +
                            "Включите автомат и нажмите\n" +
                            "в приложении кнопку старт",
                    image = ImageType.LAUNCH_ERROR_AUTOMATION
                )
            }

            9->{
                DataNotification(
                    format = FormatNotification.INSTRUCTION_NOTIFY,
                    title = "Внимание",
                    description = "Генератор не завелся\n" +
                            "Низкий уровень масла",
                    instruction = "1. Нажмите красную кнопку     \n" +
                            "на щите АВР                       \n" +
                            "2. Замените масло                      \n" +
                            "3. Поверните красную кнопку  \n" +
                            "по часосой стрелке",
                    image = ImageType.OIL
                )
            }
            10->{
                DataNotification(
                    format = FormatNotification.INSTRUCTION_NOTIFY,
                    title = "Внимание",
                    description = "Генератор не завелся!\n" +
                            "Нажата красная кнопка\n" +
                            "\n" +
                            "Поверните красную кнопку\n" +
                            "по часовой стрелке",
                    image = ImageType.BUTTON_RED
                )
            }
            11->{
                DataNotification(
                    format = FormatNotification.INSTRUCTION_NOTIFY,
                    title = "Внимание",
                    description = "Генератор не завелся\n" +
                            "Сел аккумулятор",
                    instruction = "1. Запустите генератор за шнурок\n" +
                            "2. Зарядите аккумулятор",
                    image = ImageType.BATTERY
                )
            }
            12->{
                DataNotification(
                    format = FormatNotification.INSTRUCTION_NOTIFY,
                    title = "Внимание",
                    description = "Генератор не завелся!\n" +
                            "Изношенный аккумулятор",
                    instruction = "1. Запустите генератор за шнурок\n" +
                            "2. Обязательно заменить аккумулятор  ",
                    image = ImageType.BATTERY_CRUSH
                )
            }

            13->{
                DataNotification(
                    format = FormatNotification.INSTRUCTION_NOTIFY,
                    title = "Внимание",
                    description = "Генератор заглох!\n" +
                            "Закончилось топливо  ",
                    instruction = "1. Нажмите красную кнопку   \n" +
                            "на щите АВР                   \n" +
                            "2. Залейте топливо                   \n" +
                            "3. Поверните красную кнопку\n" +
                            "по часосой стрелке  ",
                    image = ImageType.TANK
                )
            }
            14->{
                DataNotification(
                    format = FormatNotification.INSTRUCTION_NOTIFY,
                    title = "Внимание",
                    description = "До замены масла 10 часов\n" +
                            "\n" +
                            "Замените масло и обнулите счетчик\n" +
                            "Как поменять масло и обнулить счетчик смотрите в инструкции ",

                    image = ImageType.OIL
                )
            }

            15->{
                DataNotification(
                    format = FormatNotification.INSTRUCTION_DANGER,
                    title = "Внимание",
                    description = "Срочно поменяйте масло\n" +
                            "Просрочено на 10 часов\n" +
                            "\n" +
                            "Замените масло и обнулите счетчик\n" +
                            "Как поменять масло и обнулить счетчик смотрите в инструкции",

                    image = ImageType.OIL
                )
            }
            16->{
                DataNotification(
                    format = FormatNotification.ONLY_CLOSE,
                    title = "Генератор остановлен",
                    description = "Высокое напряжение\n" +
                            "генератора",

                    image = ImageType.GENERATOR_STOP_HIGH
                )
            }
            17->{
                DataNotification(
                    format = FormatNotification.ONLY_CLOSE,
                    title = "Генератор остановлен",
                    description = "Низкое напряжение\n" +
                            "генератора",

                    image = ImageType.GENERATOR_STOP_LOW
                )
            }
            18->{
                DataNotification(
                    format = FormatNotification.ONLY_OK,
                    title = "Внимание",
                    description = "Пополните баланс.\n" +
                            "\n" +
                            "Ваш номер телефона в настройках.",

                    image = ImageType.BALANCE
                )
            }
            19->{
                DataNotification(
                    format = FormatNotification.ONLY_OK,
                    title = "Внимание",
                    description = "Слабый сигнал GSM сети.\n" +
                            "\n" +
                            "Перенесите антенну в другое место.",

                    image = ImageType.GSM
                )
            }
            20->{
                DataNotification(
                    format = FormatNotification.ONLY_OK,
                    title = "Внимание",
                    description = "Мало топлива.\n" +
                            "\n" +
                            "Заправьтесь.",

                    image = ImageType.TANK
                )
            }
            21->{
                DataNotification(
                    format = FormatNotification.ALERT_DANGER,
                    title = text,
                    description = dangerText("первой",placeFirst)
                    )
            }
            22->{
                DataNotification(
                    format = FormatNotification.ALERT_DANGER,
                    title = text,
                    description = dangerText("второй",placeFirst)
                )
            }
            23->{
                DataNotification(
                    format = FormatNotification.ALERT_DANGER,
                    title = text,
                    description = dangerText("третьей",placeFirst)
                )
            }
            24->{
                DataNotification(
                    format = FormatNotification.ALERT_DANGER,
                    title = text,
                    description = dangerText("первой и третей",placeFirst)
                )
            }
            25->{
                DataNotification(
                    format = FormatNotification.ALERT_DANGER,
                    title = text,
                    description = dangerText("первой и второй",placeFirst)
                )
            }
            26->{
                DataNotification(
                    format = FormatNotification.ALERT_DANGER,
                    title = text,
                    description = dangerText("второй и третьей",placeFirst)
                )
            }
            27->{
                DataNotification(
                    format = FormatNotification.ALERT_DANGER,
                    title = text,
                    description = dangerText("первой",placeSecond)
                )
            }
            28->{
                DataNotification(
                    format = FormatNotification.ALERT_DANGER,
                    title = text,
                    description = dangerText("второй",placeSecond)
                )
            }
            29->{
                DataNotification(
                    format = FormatNotification.ALERT_DANGER,
                    title = text,
                    description = dangerText("третьей",placeSecond)
                )
            }
            30->{
                DataNotification(
                    format = FormatNotification.ALERT_DANGER,
                    title = text,
                    description = dangerText("первой и третьей",placeSecond)
                )
            }
            31->{
                DataNotification(
                    format = FormatNotification.ALERT_DANGER,
                    title = text,
                    description = dangerText("первой и второй",placeSecond)
                )
            }
            32->{
                DataNotification(
                    format = FormatNotification.ALERT_DANGER,
                    title = text,
                    description = dangerText("второй и третьей",placeSecond)
                )
            }
            33->{
                DataNotification(
                    format = FormatNotification.ALERT_NOTIFY,
                    title = low,
                    description = notifyText("первой",low)
                )
            }
            34->{
                DataNotification(
                    format = FormatNotification.ALERT_NOTIFY,
                    title = low,
                    description = notifyText("второй",low)
                )
            }
            35->{
                DataNotification(
                    format = FormatNotification.ALERT_NOTIFY,
                    title = low,
                    description = notifyText("третьей",low)
                )
            }
            36->{
                DataNotification(
                    format = FormatNotification.ALERT_NOTIFY,
                    title = low,
                    description = notifyText("первой и третьей",low)
                )
            }
            37->{
                DataNotification(
                    format = FormatNotification.ALERT_NOTIFY,
                    title = low,
                    description = notifyText("второй и третьей",low)
                )
            }
            38->{
                DataNotification(
                    format = FormatNotification.ALERT_NOTIFY,
                    title = low,
                    description = notifyText("первой и второй",low)
                )
            }
            39->{
                DataNotification(
                    format = FormatNotification.ALERT_NOTIFY,
                    title = high,
                    description = notifyText("первой",high)
                )
            }
            40->{
                DataNotification(
                    format = FormatNotification.ALERT_NOTIFY,
                    title = high,
                    description = notifyText("второй",high)
                )
            }
            41->{
                DataNotification(
                    format = FormatNotification.ALERT_NOTIFY,
                    title = high,
                    description = notifyText("третьей",high)
                )
            }
            42->{
                DataNotification(
                    format = FormatNotification.ALERT_NOTIFY,
                    title = high,
                    description = notifyText("первой и третьей",high)
                )
            }
            43->{
                DataNotification(
                    format = FormatNotification.ALERT_NOTIFY,
                    title = high,
                    description = notifyText("второй и третьей",high)
                )
            }
            44->{
                DataNotification(
                    format = FormatNotification.ALERT_NOTIFY,
                    title = high,
                    description = notifyText("первой и второй",high)
                )
            }
            45->{
                DataNotification(
                    format = FormatNotification.ALERT_NOTIFY,
                    title = "Внимание",
                    description ="Профилактический запуск"
                )
            }
            46->{
                DataNotification(
                    format = FormatNotification.ONLY_CLOSE,
                    title = "Генератор заглох",
                    description ="Проблемы с двигателем",
                    image = ImageType.GENERATOR_CRASH
                )
            }

            else->{
                DataNotification()
            }
        }
    }
}
fun dangerText(phase:String,place:String):String{
    return "С $place нет $phase фазы"
}

fun notifyText(phase: String,state:String):String{
    return "$state\n" +
            "$phase фазы"
}

