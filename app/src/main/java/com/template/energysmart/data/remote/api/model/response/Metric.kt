package com.template.energysmart.data.remote.api.model.response

import com.google.gson.annotations.SerializedName

data class Metric(
    @SerializedName("blns") val blns : String="",
    @SerializedName("bs") val bs : String="",
    @SerializedName("crds") val crds : String="",
    @SerializedName("csm") val csm : String="",
    @SerializedName("err") val err : String="",
    @SerializedName("fmw") val fmw : String="",
    @SerializedName("fuel") val fuel : String="",
    @SerializedName("gin") val gin : Double=0.0,
    @SerializedName("gpul") val gpul : Int=0,
    @SerializedName("in1") val input1 : Int=0,
    @SerializedName("in2") val input2 : Int=0,
    @SerializedName("in3") val input3 :  Int=0,
    @SerializedName("mode") val mode : String="",
    @SerializedName("odo") val odo : String="",
    @SerializedName("out") val out : String="",
    @SerializedName("p1") val p1 : String="",
    @SerializedName("p2") val p2 : String="",
    @SerializedName("p3") val p3 : String="",
    @SerializedName("pc") val pc : String="",
    @SerializedName("pg") val pg : String="",
    @SerializedName("phone") val phone : String="",
    @SerializedName("pow") val pow : String="",
    @SerializedName("rssi") val rssi : String="",
    @SerializedName("sw") val sw : String="",
    @SerializedName("t1") val temperature_generator : Int=0,
    @SerializedName("t2") val temperature_air : Int=0,
    @SerializedName("tcsw") val tcsw : Int=0,
    @SerializedName("tesw") val tesw : Int=0,
    @SerializedName("ti") val ti : String="",
    @SerializedName("time") val time : String="",
    @SerializedName("toil") val toil : String="",
    @SerializedName("ttb") val ttb : String="",
    @SerializedName("pushnot") val pushnot : String="",
    @SerializedName("lastpus") val lastpus : String="",
    @SerializedName("cmd") val cmd : String=""
)
