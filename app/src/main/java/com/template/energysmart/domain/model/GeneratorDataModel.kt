package com.template.energysmart.domain.model

import com.template.energysmart.data.remote.api.model.response.Parameter

data class GeneratorDataModel(
    val metricModel: MetricModel,
    val settingsControlModel: SettingsModel,
    val notifications:List<NotificationModel> =listOf()
    )


data class EnergyControlModel(

    val metric:GeneralMetric,
    val generalState:GeneralState,
    val power_source:PowerSource,
    val mode:Mode,
    val eco_control:EcoControl?=null,
    val notifications:List<NotificationModel> =listOf()
)

data class GeneralState(
    val phasesState:PhasesStateModel,
    val phasesNetworkState:PhasesStateModel,
    val energy_supply_home:SystemState,
    val city_network:SystemState,
    val generator_state:SystemState,
)
data class PhasesStateModel(

    val phase_state_1: SystemState,
    val phase_state_2:SystemState,
    val phase_state_3:SystemState
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


