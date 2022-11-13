package com.template.energysmart.domain.model

import com.google.gson.annotations.SerializedName

data class SettingsModel(
    val voltage_control: VoltageControl?=null,
    val eco_control:EcoControl?=null,
    val preventiveStart:PreventiveMode?=null,
    val phaseControl:PhaseControl= PhaseControl(0,true),
    val version:String="2.4",
    val balance:String="0",
    val phone:String="",
    val level_oil:String="",
    val energy:String="12.4",
    val temperatureAir:String="",
    val temperatureGenerator: String="",
    val general_odometr:Int=213,
    val odometr_before_change_oil:Int=12,
    val notifyEnabled:Boolean=false
    )
data class EcoControl(
    val time_pause: Int =0,
    val time_work: Int =0,
    val time_stop:Int = 0
)
data class VoltageControl(

    val voltage_low : Double=0.0,
    val time_low : Int=0,
    val voltage_high : Double=0.0,
    val time_high : Int=0,
)
data class PreventiveMode(

val time_work: Int=0,
val time_before_start:Int = 0)

data class PhaseControl(
    val phase_count_control:Int,
    val state:Boolean=true,
    )