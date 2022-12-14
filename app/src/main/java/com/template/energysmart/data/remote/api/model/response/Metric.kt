package com.template.energysmart.data.remote.api.model.response

import com.google.gson.annotations.SerializedName

data class Metric(
    @SerializedName("blns") val blns : Double=0.0,
    @SerializedName("state") val state : Int=0,
    @SerializedName("bs") val bs : Double=0.0,
    @SerializedName("crds") val crds : String="",
    @SerializedName("csm") val csm : String="",
    @SerializedName("err") val err : Int=0,
    @SerializedName("fmw") val fmw : Double=0.0,
    @SerializedName("fuel") val fuel : Double=0.0,
    @SerializedName("gin") val gin : Double=0.0,
    @SerializedName("gpul") val gpul : Int=0,
    @SerializedName("in1") val input1 : Double=0.0,
    @SerializedName("in2") val input2 : Double=0.0,
    @SerializedName("in3") val input3 :  Double=0.0,
    @SerializedName("mode") val mode : String="",
    @SerializedName("odo") val odo : Double=0.0,
    @SerializedName("out") val out :Double=0.0,
    @SerializedName("p1") val p1 :Double=0.0,
    @SerializedName("p2") val p2 :Double=0.0,
    @SerializedName("p3") val p3:Double=0.0,
    @SerializedName("pc") val pc:Int=0,
    @SerializedName("pg") val pg :Double=0.0,
    @SerializedName("phone") val phone : String="",
    @SerializedName("pow") val pow:Double=0.0,
    @SerializedName("rssi") val rssi  :Double=0.0,
    @SerializedName("sw") val sw :Double=0.0,
    @SerializedName("t1") val temperature_generator : Double=0.0,
    @SerializedName("t2") val temperature_air : Double=0.0,
    @SerializedName("tcsw") val tcsw : Int=0,
    @SerializedName("tesw") val tesw : Int=0,
    @SerializedName("ti") val ti :Double=0.0,
    @SerializedName("time") val time : String="",
    @SerializedName("toil") val toil  :Int=0,
    @SerializedName("ttb") val ttb :Int=0,
    @SerializedName("pushnot") val pushnot : String="",
    @SerializedName("lastpus") val lastpus : String="",
    @SerializedName("cmd") val cmd : String="",
    @SerializedName("pw1") val pw1 :Double=0.0,
    @SerializedName("pw2") val pw2 :Double=0.0,
    @SerializedName("pw3") val pw3:Double=0.0,
)
