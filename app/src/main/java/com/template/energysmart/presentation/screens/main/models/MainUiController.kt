package com.template.energysmart.presentation.screens.main.models


import android.util.Log
import com.template.energysmart.R
import com.template.energysmart.data.remote.api.model.request.Command
import com.template.energysmart.domain.model.Mode
import com.template.energysmart.domain.model.Mode.*
import com.template.energysmart.domain.model.PowerSource
import com.template.energysmart.domain.model.SystemState
import com.template.energysmart.domain.model.TemperatureState
import com.template.energysmart.presentation.data.ResourceSource
import com.template.energysmart.presentation.screens.main.MainInteractor
import com.template.energysmart.presentation.screens.main.MainViewEvent
import com.template.energysmart.presentation.screens.notifications.AlertNotificationState
import com.template.energysmart.presentation.state.DataMain
import com.template.energysmart.presentation.state.MainState
import com.template.energysmart.presentation.theme.GreenEclipse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.absoluteValue


class MainUiController( private val interactor: MainInteractor) {
    private val source=ResourceSource()
    private val _currentState= MutableStateFlow(MainViewState())
    val currentState=_currentState.asStateFlow()
    private val _actualMode= MutableStateFlow(AUTO)
    val actualMode=_actualMode.asStateFlow()
    private val _generatorCommand= MutableStateFlow(Command.START)
    val generatorCommand=_generatorCommand.asStateFlow()
    private val _sourceState= MutableStateFlow(PowerSource.NOTHING)
    val sourceState=_sourceState.asStateFlow()
   private val _id= MutableStateFlow("")
    val id=_id.asStateFlow()
    val generatorButtonImage= MutableStateFlow(R.drawable.is_start_gray)
    val generatorButtonEnabled= MutableStateFlow(false)
    val autoModeImage= MutableStateFlow(R.drawable.auto_button)
    val autoButtonIsEnabled=MutableStateFlow(false)
    val handButtonIsEnabled=MutableStateFlow(true)
    val handModeImage= MutableStateFlow(R.drawable.hand_button_gray)
    val _alertDialog= MutableStateFlow(AlertNotificationState())
    val alertDialog=_alertDialog.asStateFlow()




   fun reduceState(state: MainState.DataState):MainViewState{
       val data=state.data
       val phasesResource= source.repositoryPhases[data.power_source]
       val phaseStateFirst= phasesResource?.phaseThirdState?.get(data.generalState.phasesState.phase_state_1)
       val phaseStateSecond= phasesResource?.phaseSecondState?.get(data.generalState.phasesState.phase_state_2)
       val phaseStateThird= phasesResource?.phaseFirstState?.get(data.generalState.phasesState.phase_state_3)
       _actualMode.value=state.data.mode
       _sourceState.value=data.power_source
       Log.i("source",_sourceState.value.name)
       val stateAuto = when(state.data.mode){
            AUTO -> false
           MANUAL -> true
       }
       val stateHandle = when(state.data.mode){
           AUTO -> true
           MANUAL -> false
       }
       handButtonIsEnabled.value=stateHandle
       autoButtonIsEnabled.value=stateAuto
       autoModeImage.value=source.modeButtons[data.mode]?.first?:R.drawable.hand_button_gray
       handModeImage.value=source.modeButtons[data.mode]?.second?: R.drawable.auto_button
       generatorButtonImage.value=source.actualCommand[data.buttonState]?:R.drawable.ic_start_test
      val oilImage=source.oilImage[data.oilState]
       val batteryImage=source.batteryImage[data.batteryState]
       val cold= data.temperatureState==TemperatureState.MINUS


      val stateCommand = when(state.data.power_source){
          PowerSource.NETWORK -> actualMode.value != AUTO
          PowerSource.GENERATOR -> true
          PowerSource.NOTHING -> false
      }
       generatorButtonEnabled.value=stateCommand
       _currentState.value =MainViewState(
           electricNetworkImage = source.network[data.generalState.city_network]?: R.drawable.line_electro,
           homeImage = source.home[data.generalState.energy_supply_home]?:R.drawable.ic_home_green,
           generatorImage = source.generator[data.generalState.generator_state]?:R.drawable.generator_off,
           phase_vol_3 = data.metric.voltage_3,
           phase_vol_2 = data.metric.voltage_2,
           phase_vol_1 = data.metric.voltage_1,
           oilText = data.metric.time_to_change_oil,
           temperature = data.metric.temperature,
           stationText = data.metric.oil_level,
           commandButton = source.actualCommand[data.buttonState]?:R.drawable.ic_start_test,
           phaseFirstImage = phaseStateFirst?.first?:R.drawable.phase_generator_1_green,
           phasePointFirst = phaseStateFirst?.second?:R.drawable.round_phase_1,
           phaseImageSecond = phaseStateSecond?.first?:R.drawable.phase_generator_1_green,
           phasePointSecond = phaseStateSecond?.second?:R.drawable.round_phase_1,
           phaseImageThird = phaseStateThird?.first?:R.drawable.phase_generator_1_green,
           phasePointThird = phaseStateThird?.second?:R.drawable.round_phase_1,
           commandButtonIsEnabled = stateCommand,
           autoButtonIsEnabled = stateAuto,
           handButtonIsEnabled = stateHandle,
           manualButton = source.modeButtons[data.mode]?.second?: R.drawable.auto_button,
           autoButton = source.modeButtons[data.mode]?.first?:R.drawable.hand_button_gray,
           pNetworkImageFirst = source.pointNetworkState[data.generalState.phasesNetworkState.phase_state_1]?:R.drawable.point_network_green  ,
           pNetworkImageSecond = source.pointNetworkState[data.generalState.phasesNetworkState.phase_state_2]?:R.drawable.point_network_green,
           pNetworkImageThird = source.pointNetworkState[data.generalState.phasesNetworkState.phase_state_3]?:R.drawable.point_network_green,
           timeText = state.data.metric.time_work_timer,
           eclipseColor = source.eclipse[data.generalState.eclipseState]?: GreenEclipse,
           source = state.data.power_source,
           oilImage = oilImage?:R.drawable.ic_vector,
           batteryImage = batteryImage?:R.drawable.ic_full_battery,
           cold = cold

       )


     return  _currentState.value

   }

    fun handleEvent(event: MainViewEvent)=when(event){
        is MainViewEvent.ModeEvent->{
            if (actualMode.value==MANUAL) {
                if(sourceState.value==PowerSource.NETWORK) {
                    generatorButtonImage.value = R.drawable.is_start_gray
                    generatorButtonEnabled.value=false
                }else {
                    generatorButtonEnabled.value=true

                }
                handModeImage.value = R.drawable.hand_button_gray
                autoModeImage.value = R.drawable.auto_button
                autoButtonIsEnabled.value = false
               handButtonIsEnabled.value = true

                interactor.updateMode("AUTO")


                _actualMode.value=AUTO

            }
            else {
                if(sourceState.value==PowerSource.NETWORK) {
                    generatorButtonImage.value = R.drawable.ic_start_test
                    generatorButtonEnabled.value=true

                }
                else {
                    generatorButtonEnabled.value=true
                }
                handModeImage.value = R.drawable.hand_button_green
             autoModeImage.value = R.drawable.auto_button_gray
               autoButtonIsEnabled.value = true
                handButtonIsEnabled.value = false

                interactor.updateMode("MANUAL")

            _actualMode.value=MANUAL
            }
        }
        is MainViewEvent.GeneratorCommandEvent ->{
            when(sourceState.value){
                PowerSource.NETWORK->{
                    _generatorCommand.value=Command.START
                    interactor.sendCommand("START")
                   generatorButtonImage.value=R.drawable.ic_stop_test
                    _sourceState.value=PowerSource.GENERATOR
                }
                PowerSource.GENERATOR ->{
                    _generatorCommand.value=Command.STOP
                    interactor.sendCommand("STOP")
                   generatorButtonEnabled.value=false
                   generatorButtonImage.value = R.drawable.is_start_gray

                    _sourceState.value=PowerSource.NETWORK
                }
                else -> {}
            }
        }
        is MainViewEvent.CloseAlertEvent ->{
            _alertDialog.value= AlertNotificationState()
            interactor.closeAlert(event.id)
        }
    }





}