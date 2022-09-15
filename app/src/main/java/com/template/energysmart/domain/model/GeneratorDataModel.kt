package com.template.energysmart.domain.model

import com.template.energysmart.data.remote.api.model.response.Parameter

data class GeneratorDataModel(
    val metricModel: MetricModel,
    val settingsControlModel: SettingsModel,
    val notifications:List<NotificationModel> =listOf()
    )
data class EnergyControlModel(
    val metric:GeneralMetric= GeneralMetric(),
    val generalState:GeneralState= GeneralState(),
    val power_source:PowerSource=PowerSource.NETWORK,
    val mode:Mode=Mode.AUTO,
    val eco_control:EcoControl?=null,

    )

data class GeneralState(
    val phasesState:PhasesStateModel= PhasesStateModel(),
    val phasesNetworkState:PhasesStateModel= PhasesStateModel(),
    val energy_supply_home:SystemState=SystemState.STABLE,
    val city_network:SystemState=SystemState.DISABLED,
    val generator_state:SystemState=SystemState.STABLE,
)
data class PhasesStateModel(

    val phase_state_1: SystemState=SystemState.DISABLED,
    val phase_state_2:SystemState=SystemState.DISABLED,
    val phase_state_3:SystemState=SystemState.DISABLED
)

data class GeneralMetric(
    val time_to_change_oil:String="",
    val time_work_timer:String="",
    val temperature:Int=0,
    val oil_level:String="",
    val voltage_1:Int=0,
    val voltage_2:Int=0,
    val voltage_3:Int=0,
)

enum class SystemState {
    DISABLED,
    STABLE,
    WARNING,
    CRITICAL
}
enum class PowerSource{
    NETWORK,
    GENERATOR
}
enum class Mode{
    AUTO,
    MANUAL
}


