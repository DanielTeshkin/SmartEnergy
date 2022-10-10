package com.template.energysmart.data.remote.api.model.request

import com.google.gson.annotations.SerializedName

data class ParameterRequest(
    @SerializedName("toil") val toil : Double=0.0,
    @SerializedName("reset_oil_change") val reset_oil_change : Double=0.0,
    @SerializedName("phasescount") val phasescount : Boolean=true,
    @SerializedName("phasecontrol") val phasecontrol : Int=0,
    @SerializedName("voltage_control") val voltage_control : Boolean=false,
    @SerializedName("ext_voltage_phase_low") val ext_voltage_phase_low : Double=0.0,
    @SerializedName("ext_voltage_time_phase_low") val ext_voltage_time_phase_low : Int=0,
    @SerializedName("ext_voltage_phase_high") val ext_voltage_phase_high :Double=0.0,
    @SerializedName("ext_voltage_time_phase_high") val ext_voltage_time_phase_high : Int=0,
    @SerializedName("notifDisabled") val notifDisabled : Boolean=false,
    @SerializedName("ecoEnable") val ecoEnable : Boolean= false,
    @SerializedName("pause_before_starting_the_generator") val pause_before_starting_the_generator : Int=0,
    @SerializedName("eco_generator_run_time") val eco_generator_run_time : Int=0,
    @SerializedName("downtime") val downtime : Int=0,
    @SerializedName("fuel") val fuel : Boolean=false,
    @SerializedName("start_time") val start_time : Int=0,
    @SerializedName("prof_generator_run_time") val prof_generator_run_time : Int=0,
)