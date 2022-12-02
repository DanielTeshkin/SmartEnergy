package com.template.energysmart.domain.mappers

import android.util.Log
import com.template.energysmart.data.remote.api.model.response.Device
import com.template.energysmart.data.remote.api.model.response.Metric
import com.template.energysmart.data.remote.api.model.response.Parameter
import com.template.energysmart.domain.model.*
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun map(model: Metric, parameter: Parameter, device: Device): EnergyControlModel {
        val systemStateController=
            SystemStateController(model.input1.toInt(),
                model.input2.toInt(),
                model.input3.toInt(),
                model.gpul,
                model.gin.toInt(),
                phaseControl = if(parameter.phasescount) 0 else parameter.phasecontrol,model.sw.toInt())

        val timeToChangeOil=model.toil/3600
        val mode=if(model.mode=="AUTO") "AUTO" else "MANUAL"

        Log.i("q",timeToChangeOil.toString())
        val generalVoltage=(model.pw1+model.pw2+model.pw3).toFloat()
        Log.i("vol",generalVoltage.toFloat().toString())

        val worker=if(model.bs.toInt()==0&&model.tesw==0&&model.ttb==0)model.tcsw else 0
        val timeShow=worker

        Log.i("ttb",(model.ttb/3600000).toString())
        val metrics= when(model.sw.toInt()) {
               1 -> GeneralMetric(
                   voltage_1 = model.pw1,
                   voltage_2 = model.pw2,
                   voltage_3 = model.pw3,
                   temperature = model.temperature_air.toInt(),
                   oil_level = model.fuel.toString(),
                   time_to_change_oil =timeToChangeOil.toString(),
                   time_work_timer = (model.odo/3600000).toInt().toString(),
                   network_1 = model.input1.toInt(),
                   network_2 = model.input2.toInt(),
                   network_3 = model.input3.toInt(),
                   general = generalVoltage,
                   time=timeShow

               )
               2 -> GeneralMetric(
                   time_to_change_oil = timeToChangeOil.toString(),
                   time_work_timer = (model.odo/3600000).toInt().toString()
                   ,
                   temperature = model.temperature_air.toInt(),
                   oil_level = model.fuel.toString(),
                   voltage_1 = model.gin,
                   voltage_2
                   = model.gin,
                   voltage_3 = model.gin,
                   network_1 = model.input1.toInt(),
                   network_2 = model.input2.toInt(),
                   network_3 = model.input3.toInt(),
                   general = generalVoltage,
                   time = timeShow
               )
               else ->  GeneralMetric(
                   time_to_change_oil = timeToChangeOil.toString(),
                   time_work_timer = (model.odo/3600000).toInt().toString(),
                   temperature = model.temperature_air.toInt(),
                   oil_level = model.fuel.toString(),
                   voltage_1 =0.0,
                   voltage_2 =0.0,
                   voltage_3 =0.0,
                   network_1 = model.input1.toInt(),
                   network_2 = model.input2.toInt(),
                   network_3 = model.input3.toInt(),
                   general = generalVoltage,
                   time = timeShow
               )
           }
        val generalState=systemStateController.getGeneralState()
        val buttonState=if(model.bs.toInt()==1) ButtonState.GRAY else checkButtonState(model, mode)
        val oilState=if(timeToChangeOil>10)OilState.OK else if(timeToChangeOil in 0..10) OilState.WARNING else OilState.CRITICAL
        val temperatureState=if(model.temperature_air.toInt()<0)TemperatureState.MINUS else TemperatureState.PLUS
        val batteryState= when {
            model.rssi>-65.0 -> BatteryState.FULL
            model.rssi in -65.00..-75.00 -> BatteryState.THREE
            model.rssi in -75.00..-85.00 -> BatteryState.HALF
            else -> BatteryState.SINGLE
        }

        return EnergyControlModel(
            generalState = generalState,
            metric = metrics,
            eco_control = if(mode=="AUTO")   EcoControl(
                time_pause = parameter.pause_before_starting_the_generator,
                time_stop = parameter.downtime,
                time_work = parameter.eco_generator_run_time
            ) else null,
            power_source =when(model.sw.toInt()) {
                1-> PowerSource.NETWORK
                2-> PowerSource.GENERATOR
                else ->PowerSource.NOTHING
            }
            ,
            mode = Mode.valueOf(mode),
            buttonState = buttonState,
            oilState = oilState,
            temperatureState = temperatureState,
            batteryState = batteryState

        )
    }

    private fun checkButtonState(model: Metric,mode:String)=when(model.state){
        0-> if(mode=="AUTO")  ButtonState.GRAY else ButtonState.GREEN
        1-> ButtonState.RED
        else->ButtonState.GRAY
    }

    fun mapParameter(parameter: Parameter, metrics: Metric)= SettingsModel(
        voltage_control =
        when (parameter.voltage_control) {
            true -> VoltageControl(
                state=true,
                time_high = parameter.ext_voltage_time_phase_high,
                time_low = parameter.ext_voltage_time_phase_low,
                voltage_high = parameter.ext_voltage_phase_high,
                voltage_low = parameter.ext_voltage_phase_low
            )
            false -> VoltageControl(
                state=false,
                time_high = parameter.ext_voltage_time_phase_high,
                time_low = parameter.ext_voltage_time_phase_low,
                voltage_high = parameter.ext_voltage_phase_high,
                voltage_low = parameter.ext_voltage_phase_low
            )
        },
        eco_control =
        when(parameter.ecoEnable) {
            true ->
                EcoControl(
                    state=true,
                    time_pause = parameter.pause_before_starting_the_generator,
                    time_stop = parameter.downtime,
                    time_work = parameter.eco_generator_run_time
                )
            false ->  EcoControl(
                state=false,
                time_pause = parameter.pause_before_starting_the_generator,
                time_stop = parameter.downtime,
                time_work = parameter.eco_generator_run_time
            )
        },
        preventiveStart = when(parameter.fuel) {
            true->  PreventiveMode(
                state=true,
                time_work = parameter.prof_generator_run_time,
                time_before_start = parameter.start_time
            )
            false -> PreventiveMode(
                state=false,
                time_work = parameter.prof_generator_run_time,
                time_before_start = parameter.start_time
            )
        },
        phaseControl = when(parameter.phasescount){
            true-> PhaseControl(phase_count_control = parameter.phasecontrol,state=true)
            false -> PhaseControl(phase_count_control = parameter.phasecontrol,state=false)
        },
        balance = metrics.blns.toString(),
        phone =metrics.phone,
        version = metrics.fmw.toString(),
        energy = metrics.ti.toString(),
        level_oil = metrics.pow.toString(),
        general_odometr = metrics.toil.toInt(),
        odometr_before_change_oil =parameter.reset_oil_change.toInt(),
        temperatureAir = metrics.temperature_air.toString(),
        temperatureGenerator = metrics.temperature_generator.toString(),
        notifyEnabled = parameter.notifDisabled
    )

    fun mapNotification(code:Int): DataNotification {
        val text="Обрыв фазы"
        val placeFirst="C города"
        val placeSecond="В доме"
        val low="Низкое напряжение"
        val high="Высокое напряжение"
        return when(code){
            1-> DataNotification(
                format = FormatNotification.SEND_COMMAND_NOTIFY,
                title ="Внимание",
                description = "С города выключили электрчество",
                action="Запускаем генератор?",
                image = ImageType.CITY_OFF
            )
            2-> DataNotification(
                format = FormatNotification.SEND_COMMAND_PROBLEM,
                title = "Ошибка запуска",
                description = "Генератор не завелся,\n" +
                        "Неизвестная ошибка",
                action="Попробуем ещё раз?",
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
    return "$place нет $phase фазы"
}

fun notifyText(phase: String,state:String):String{
    return "$state\n" +
            "$phase фазы"
}

