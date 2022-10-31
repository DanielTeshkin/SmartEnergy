package com.template.energysmart.domain.mappers

import com.template.energysmart.domain.model.*
import kotlin.math.min

class SystemStateController(private val voltage1:Int,
                            private val voltage2:Int,
                            private val voltage3:Int,
                            private val voltageGenerator:Int,
                            private val voltage:Int,
                            private val phaseControl: Int,
                            private val source:Int
                            ) {


    fun getGeneralState()=GeneralState(
        phasesState = getPhasesState(),
        energy_supply_home = getHomeState(),
        city_network = getNetworkState(),
        generator_state = getGeneratorState(),
        phasesNetworkState = getNetworkPhase(),
        eclipseState = getEclipseState()
    )
     private fun getNetworkPhase()=PhasesStateModel(
         phase_state_1 =checkedState(voltage1),
         phase_state_2 = checkedState(voltage2),
         phase_state_3 = checkedState(voltage3)
     )
   private fun getPhasesState()= when(source) {
      1-> getNetworkPhase()
       2-> { PhasesStateModel(getGeneratorState(),getGeneratorState(),getGeneratorState()) }
       else -> PhasesStateModel()
   }

    private fun getEclipseState()=when(voltageGenerator){
        in 0..17 -> if(source==2)SystemState.CRITICAL else SystemState.DISABLED
        else -> if(source==2)SystemState.STABLE else SystemState.DISABLED
    }

    private fun getHomeState() = when(source){
        1->getNetworkState()
        2->getGeneratorState()
        else ->SystemState.DISABLED
    }
    private fun checkedState(voltage:Int):SystemState =
        when(voltage){
            in 0..100->SystemState.DISABLED
            in 101..170->SystemState.CRITICAL
            in 171..200->SystemState.WARNING
            in 201..240->SystemState.STABLE
            in 240..250->SystemState.WARNING
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

    private fun getGeneratorState()=checkedState(voltage)

}