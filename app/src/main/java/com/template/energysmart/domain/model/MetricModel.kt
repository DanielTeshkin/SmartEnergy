package com.template.energysmart.domain.model

data class MetricModel(
    val phase_first:Int=0,
    val phase_second: Int=0,
    val phase_third:Int=0,
    val time:String="",
    val temperature:Int=0,
    val oil_level:String="",
    val mode:String="",
    val command:String=""
)

