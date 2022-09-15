package com.template.energysmart.presentation.screens.main.models


import com.template.energysmart.R
import com.template.energysmart.domain.model.Mode
import com.template.energysmart.domain.model.Mode.*
import com.template.energysmart.domain.model.PowerSource
import com.template.energysmart.domain.model.SystemState
import com.template.energysmart.presentation.data.ResourceSource
import com.template.energysmart.presentation.screens.main.MainViewEvent
import com.template.energysmart.presentation.state.DataMain
import com.template.energysmart.presentation.state.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class MainUiController(){

   private val source=ResourceSource()


   fun reduceState(state: MainState.DataState):MainViewState{
       val data=state.data
      val phasesResource= source.repositoryPhases[data.power_source]
       val phaseStateFirst= phasesResource?.phaseThirdState?.get(data.generalState.phasesState.phase_state_1)
       val phaseStateSecond= phasesResource?.phaseSecondState?.get(data.generalState.phasesState.phase_state_2)
       val phaseStateThird= phasesResource?.phaseFirstState?.get(data.generalState.phasesState.phase_state_3)
       val stateAuto = when(state.data.mode){
            AUTO -> true
           MANUAL -> false
       }
       val stateHandle = when(state.data.mode){
           AUTO -> false
           MANUAL -> true
       }
      val stateCommand = when(state.data.power_source){
          PowerSource.NETWORK -> true
          PowerSource.GENERATOR -> false
      }


     return  MainViewState(
         electricNetworkImage = source.network[data.generalState.city_network]?: R.drawable.line_electro,
         homeImage = source.home[data.generalState.energy_supply_home]?:R.drawable.ic_home_green,
         imageStateGenerator = source.generator[data.generalState.generator_state],
         phase_vol_3 = data.metric.voltage_3,
         phase_vol_2 = data.metric.voltage_2,
         phase_vol_1 = data.metric.voltage_1,
         oilText = data.metric.time_to_change_oil,
         temperature = data.metric.temperature,
         stationText = data.metric.oil_level,
         commandButton = source.actualCommand[data.power_source]?:R.drawable.ic_start_test,
         phaseFirstImage = phaseStateFirst?.first?:R.drawable.phase_generator_1_green,
         phasePointFirst = phaseStateFirst?.second?:R.drawable.round_phase_1,
         phaseImageSecond = phaseStateSecond?.first?:R.drawable.phase_generator_1_green,
         phasePointSecond = phaseStateSecond?.second?:R.drawable.round_phase_1,
         phaseImageThird = phaseStateThird?.first?:R.drawable.phase_generator_1_green,
         phasePointThird = phaseStateThird?.second?:R.drawable.round_phase_1,
         commandButtonIsEnabled = stateCommand,
         autoButtonIsEnabled = stateAuto,
         handButtonIsEnabled = stateHandle,
         manualButton = source.handle[data.mode]?:R.drawable.hand_button_gray,
         autoButton = source.auto[data.mode]?:R.drawable.hand_button_gray,
         pNetworkImageFirst = source.pointNetworkState[data.generalState.phasesNetworkState.phase_state_1]?:R.drawable.point_network_green  ,
         pNetworkImageSecond = source.pointNetworkState[data.generalState.phasesNetworkState.phase_state_2]?:R.drawable.point_network_green,
         pNetworkImageThird = source.pointNetworkState[data.generalState.phasesNetworkState.phase_state_3]?:R.drawable.point_network_green,
         timeText = state.data.metric.time_work_timer
     )

   }


    private fun setConfig(data:DataMain){
     //  _ui.value= MainViewState(
         //    phase_vol_1 = data.metric?.input1.toString(),
        //     phase_vol_2 = data.metric?.input2.toString(),
         //    phase_vol_3 = data.metric?.input3.toString(),
         //  temperature = data.metric?.temperature_air.toString(),
         //  timeText = data.parameter?.reset_oil_change.toString()



       //)
    }

    fun reduce(mainViewEvent: MainViewEvent){

    }

}