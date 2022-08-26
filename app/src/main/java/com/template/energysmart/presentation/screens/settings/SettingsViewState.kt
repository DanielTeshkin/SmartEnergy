package com.template.energysmart.presentation.screens.settings

import com.template.energysmart.domain.model.PreventiveMode
import com.template.energysmart.domain.model.VoltageControl

data class SettingsViewState(
    val loading:Boolean=false,
    val error:String="",
    val generalSettingsData: GeneralSettingsData= GeneralSettingsData(),
    val general_odometr:String ="231",
    val odometr_before_change_oil:String ="12",
    val notifyEnabled:Boolean=false,
    val phaseControl:Boolean=true,
    val phaseFirstState:Boolean=false,
    val phaseSecondState:Boolean=false,
    val phaseThirdState:Boolean=false,
    val ecoEnable:Boolean=false,
    val time_pause: Int =0,
    val time_work: Int =0,
    val time_stop:Int = 0,
    val preventiveMode: Boolean=true,
    val time_workPreventive: Int=0,
    val time_before_start_preventive:Int = 0,
    val voltageControl: Boolean=false,
    val voltage_low : Int=0,
    val time_low : Int=0,
    val voltage_high : Int=200,
    val time_high : Int=300,

    )

data class GeneralSettingsData(
    val version:String="2.4",
    val balance:String="0",
    val phone:String="79257777",
    val level_oil:String="444444",
    val energy:String="12.4",
    val temperatureAir:String="12",
    val temperatureGenerator: String="12",

)