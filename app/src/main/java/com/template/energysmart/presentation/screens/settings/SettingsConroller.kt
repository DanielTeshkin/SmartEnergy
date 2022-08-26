package com.template.energysmart.presentation.screens.settings


import com.template.energysmart.presentation.screens.settings.model.SettingsPresentationState
import com.template.energysmart.presentation.screens.settings.model.UpdateSettingsModel
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsController() {
    private val phaseControl= MutableStateFlow(false)
    private val voltageControl= MutableStateFlow(false)
    private val ecoMode= MutableStateFlow(false)
    private val preventiveMode= MutableStateFlow(false)
    private val notifyEnabled= MutableStateFlow(false)
    private val timeLowVoltage= MutableStateFlow(0)
    private val timeHighVoltage= MutableStateFlow(0)
    private val voltageLow=MutableStateFlow(0.0)
    private val voltageHigh=MutableStateFlow(0.0)
    private val timePause=MutableStateFlow(0)
    private val timeStop=MutableStateFlow(0)
    private val timeWork=MutableStateFlow(0)

    private val phaseFirstState=MutableStateFlow(false)
    private val phaseSecondState=MutableStateFlow(false)
    private val phaseThirdState=MutableStateFlow(false)
    private val generalData= MutableStateFlow(GeneralSettingsData())
    private val generalOdometr = MutableStateFlow("")
    private val odometrToChangeOil= MutableStateFlow("")
    private val timeWorkPreventive = MutableStateFlow(0)
    private val  timeBeforeStartPreventive= MutableStateFlow(0)
    private val loading= MutableStateFlow(false)
    private val error= MutableStateFlow("")
    private val state= MutableStateFlow(SettingsViewState())

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
                3->phaseThirdState.value
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
        general_odometr = generalOdometr.value,
        odometr_before_change_oil =odometrToChangeOil.value,
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
            is SettingsViewEvent.ResetOdometrEvent -> generalOdometr.value = "0"
            is SettingsViewEvent.ResetOdometrToChangeOilEvent -> odometrToChangeOil.value = "0"
            is SettingsViewEvent.CheckedChangeEvent -> reduceCheckedChangeEvent(event)
            is SettingsViewEvent.ValueChangerEvent -> reduceValueChangeEvent(event)
        }
    return getCurrentState()
    }

    private fun reduceValueChangeEvent(event: SettingsViewEvent.ValueChangerEvent) {
        when(event.parameter){
            ParameterValueType.VOLTAGE_LOW -> voltageLow.value=event.input.toDouble()
            ParameterValueType.VOLTAGE_HIGH -> voltageHigh.value=event.input.toDouble()
            ParameterValueType.TIME_LOW -> timeLowVoltage.value=event.input.toInt()
            ParameterValueType.TIME_HIGH -> timeHighVoltage.value=event.input.toInt()
            ParameterValueType.TIME_PAUSE -> timePause.value=event.input.toInt()
            ParameterValueType.TIME_WORK -> timeWork.value=event.input.toInt()
            ParameterValueType.TIME_STOP -> timeStop.value=event.input.toInt()
            ParameterValueType.TIME_BEFORE_START -> timeBeforeStartPreventive.value=event.input.toInt()
            ParameterValueType.TIME_PREVENTIVE_WORK -> timeWorkPreventive.value=event.input.toInt()
        }


    }




    private fun reduceCheckedChangeEvent(event:SettingsViewEvent.CheckedChangeEvent){
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
        generalOdometr.value.toInt(),
        odometrToChangeOil.value.toDouble()
    )


}