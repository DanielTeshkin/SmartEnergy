package com.template.energysmart.domain.mappers

import com.template.energysmart.domain.model.*
import kotlin.math.min

class SystemStateController(private val voltage1:Int,
                            private val voltage2:Int,
                            private val voltage3:Int,
                            private val voltageGenerator:Int,
                            private val source:String,
                            private val phaseControl: Int
                            ) {


    fun getGeneralState()=GeneralState(
        phasesState = getPhasesState(),
        energy_supply_home = getHomeState(),
        city_network = getNetworkState(),
        generator_state = getGeneratorState(),
        phasesNetworkState = getNetworkPhase()
    )
     private fun getNetworkPhase()=PhasesStateModel(
         phase_state_1 =checkedState(voltage1),
         phase_state_2 = checkedState(voltage2),
         phase_state_3 = checkedState(voltage3)
     )
   private fun getPhasesState()= when(source) {
      "STOP"-> getNetworkPhase()
       "START"-> {
           val state=getGeneratorState()
           PhasesStateModel(state,state,state)
       }
       else ->getNetworkPhase()
   }

    private fun getHomeState() = when(source){
        "START"->getGeneratorState()
        "STOP"->getNetworkState()
        else ->getNetworkState()
    }
    private fun checkedState(voltage:Int):SystemState =
        when(voltage){
            in 0..100->SystemState.DISABLED
            in 101..170->SystemState.CRITICAL
            in 171..200->SystemState.WARNING
            in 201..240->SystemState.STABLE
            else -> SystemState.CRITICAL
        }

    private fun getNetworkState():SystemState{
       return when(phaseControl){
            0->{
                 val min= minOf(voltage1,voltage2,voltage3)
                val max= maxOf(voltage1,voltage2,voltage3)
                val maxState=checkedState(max)
                val minState=checkedState(min)
                 return when (maxState==SystemState.CRITICAL){
                     true->maxState
                     false->minState
                 }

            }
           1-> checkedState(voltage1)
           2-> checkedState(voltage2)
           3-> checkedState(voltage3)
           else->SystemState.DISABLED
       }
    }

    private fun getGeneratorState()=checkedState(voltageGenerator)

}