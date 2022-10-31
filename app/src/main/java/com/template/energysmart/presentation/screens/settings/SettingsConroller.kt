package com.template.energysmart.presentation.screens.settings


import androidx.core.text.isDigitsOnly
import com.template.energysmart.presentation.screens.settings.model.SettingsPresentationState
import com.template.energysmart.presentation.screens.settings.model.UpdateSettingsModel
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsController() {
    val phaseControl= MutableStateFlow(false)
     val voltageControl= MutableStateFlow(false)
     val ecoMode= MutableStateFlow(false)
    val preventiveMode= MutableStateFlow(false)
  val notifyEnabled= MutableStateFlow(false)
 val timeLowVoltage= MutableStateFlow(0)
    val timeHighVoltage= MutableStateFlow(0)
     val voltageLow=MutableStateFlow(0.0)
     val voltageHigh=MutableStateFlow(0.0)
    val timePause=MutableStateFlow(0)
     val timeStop=MutableStateFlow(0)
     val timeWork=MutableStateFlow(0)

     val phaseFirstState=MutableStateFlow(false)
     val phaseSecondState=MutableStateFlow(false)
    val phaseThirdState=MutableStateFlow(false)
     val generalData= MutableStateFlow(GeneralSettingsData())
    val generalOdometr = MutableStateFlow(231)
     val odometrToChangeOil= MutableStateFlow(12)
     val timeWorkPreventive = MutableStateFlow(0)
     val  timeBeforeStartPreventive= MutableStateFlow(0)
    val loading= MutableStateFlow(false)
     val error= MutableStateFlow("")
     val phaseCount=MutableStateFlow(0)


    fun getStartViewState(data: SettingsPresentationState):SettingsViewState {
        data.apply {
            this@SettingsController.error.value = error
            this@SettingsController.loading.value=loading
            generalData.value=GeneralSettingsData(
                version = settingsModel.version,
                phone =settingsModel.phone,
                balance = settingsModel.balance,
                level_oil = settingsModel.level_oil,
                energy = settingsModel.energy,
                temperatureGenerator =settingsModel.temperatureGenerator,
                temperatureAir = settingsModel.temperatureAir,
            )
            notifyEnabled.value=settingsModel.notifyEnabled
            generalOdometr.value=settingsModel.general_odometr
            odometrToChangeOil.value=settingsModel.odometr_before_change_oil
            ecoMode.value= settingsModel.eco_control != null
            timePause.value=settingsModel.eco_control?.time_pause?: 0
            timeWork.value=settingsModel.eco_control?.time_work?: 0
            timeStop.value=settingsModel.eco_control?.time_stop?: 0
            voltageControl.value=data.settingsModel.voltage_control != null
            timeLowVoltage.value=data.settingsModel.voltage_control?.time_low ?: 0
            timeHighVoltage.value=data.settingsModel.voltage_control?.time_high?: 0
            voltageHigh.value=data.settingsModel.voltage_control?.voltage_high?: 0.0
            voltageLow.value=data.settingsModel.voltage_control?.voltage_low?: 0.0
            phaseControl.value=settingsModel.phaseControl != null
            when(settingsModel.phaseControl?.phase_count_control){
                1-> phaseFirstState.value=true
                2->phaseSecondState.value=true
                3->phaseThirdState.value=true
            }
            preventiveMode.value=settingsModel.preventiveStart != null
            timeBeforeStartPreventive.value=data.settingsModel.preventiveStart?.time_before_start
                ?: 0
            timeWorkPreventive.value=settingsModel.preventiveStart?.time_work ?: 0
        }

      return  getCurrentState()
    }


    private fun getCurrentState() =SettingsViewState(
        loading = loading.value,
        error = error.value,
        generalSettingsData = generalData.value,
        general_odometr = generalOdometr.value.toString(),
        odometr_before_change_oil = odometrToChangeOil.value.toString(),
        notifyEnabled = notifyEnabled.value,
        ecoEnable = ecoMode.value,
        preventiveMode = preventiveMode.value,
        voltageControl = voltageControl.value,
        phaseControl = phaseControl.value,
        phaseFirstState = phaseFirstState.value,
        phaseSecondState = phaseSecondState.value,
        phaseThirdState = phaseThirdState.value,
        time_high = timeHighVoltage.value,
        time_low = timeLowVoltage.value,
        time_before_start_preventive = timeBeforeStartPreventive.value,
        time_workPreventive = timeWorkPreventive.value,
        time_stop = timeStop.value,
        time_work = timeWork.value,
        time_pause = timePause.value,
        voltage_high = voltageHigh.value.toInt(),
        voltage_low = voltageLow.value.toInt()
    )

    fun reduceEvent(event: SettingsViewEvent): SettingsViewState {
        when (event) {
            is SettingsViewEvent.ResetOdometrEvent -> generalOdometr.value = 0
            is SettingsViewEvent.ResetOdometrToChangeOilEvent -> odometrToChangeOil.value = 0
            is SettingsViewEvent.SwitchStateChangeEvent-> reduceCheckedChangeEvent(event)
            is SettingsViewEvent.ValueChangerEvent -> reduceValueChangeEvent(event)
            is SettingsViewEvent.СhoicePhaseEvent -> reduceChoicePhaseEvent(event)
        }
    return getCurrentState()
    }

    private fun reduceChoicePhaseEvent(event: SettingsViewEvent.СhoicePhaseEvent) {
        if (event.state) phaseCount.value = event.number
    }





    private fun reduceValueChangeEvent(event: SettingsViewEvent.ValueChangerEvent) {
        val input=event.input
        when (event.parameter) {
            ParameterValueType.VOLTAGE_LOW -> voltageLow.value = update(input,voltageLow.value)
            ParameterValueType.VOLTAGE_HIGH -> voltageHigh.value = update(input,voltageHigh.value)
            ParameterValueType.TIME_LOW -> timeLowVoltage.value = update(input,timeLowVoltage.value.toDouble()).toInt()
            ParameterValueType.TIME_HIGH -> timeHighVoltage.value = update(input,timeHighVoltage.value.toDouble()).toInt()
            ParameterValueType.TIME_PAUSE -> timePause.value =update(input,timePause.value.toDouble()).toInt()
            ParameterValueType.TIME_WORK -> timeWork.value = update(input,timeWork.value.toDouble()).toInt()
            ParameterValueType.TIME_STOP -> timeStop.value = update(input,timeStop.value.toDouble()).toInt()
            ParameterValueType.TIME_BEFORE_START -> timeBeforeStartPreventive.value =
                update(input,timeBeforeStartPreventive.value.toDouble()).toInt()
            ParameterValueType.TIME_PREVENTIVE_WORK -> timeWorkPreventive.value =
                update(input,timeWorkPreventive.value.toDouble()).toInt()
        }


    }
    private fun  update(input:String,current:Double)=if (input.isDigitsOnly()) input.toDouble() else current



    private fun reduceCheckedChangeEvent(event:SettingsViewEvent.SwitchStateChangeEvent){
            when(event.parameter){
           ParameterType.ECO->ecoMode.value=event.state
            ParameterType.VOLTAGE_CONTROL->voltageControl.value=event.state
            ParameterType.NOTIFY_ENABLED -> notifyEnabled.value=event.state
                ParameterType.PHASE_CONTROL -> phaseControl.value=event.state
                ParameterType.PREVENTIVE->preventiveMode.value=event.state

            } }

    fun getUpdateModel()=UpdateSettingsModel(
        voltageControl.value,
        ecoMode.value,
        phaseControl.value,
        notifyEnabled.value,
        preventiveMode.value,
        voltageLow.value,
        voltageHigh.value,
        timeLowVoltage.value,
        timeHighVoltage.value,
        timeWork.value,
        timeStop.value,
        timePause.value,
        timeWorkPreventive.value,
        timeBeforeStartPreventive.value,
        generalOdometr.value,
        odometrToChangeOil.value,
        phaseCount.value
    )


}