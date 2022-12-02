package com.template.energysmart.domain.model

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
    val buttonState: ButtonState=ButtonState.GRAY,
    val eco_control:EcoControl?=null,
    val temperatureState: TemperatureState,
    val oilState: OilState,
    val batteryState: BatteryState

    )

data class GeneralState(
    val phasesState:PhasesStateModel= PhasesStateModel(),
    val phasesNetworkState:PhasesStateModel= PhasesStateModel(),
    val energy_supply_home:SystemState=SystemState.STABLE,
    val city_network:SystemState=SystemState.DISABLED,
    val generator_state:SystemState=SystemState.STABLE,
    val eclipseState:SystemState=SystemState.DISABLED,
)
data class PhasesStateModel(
    val phase_state_1: SystemState=SystemState.DISABLED,
    val phase_state_2:SystemState=SystemState.DISABLED,
    val phase_state_3:SystemState=SystemState.DISABLED
)

data class GeneralMetric(
    val time_to_change_oil: String = "",
    val time_work_timer: String = "",
    val temperature: Int = 0,
    val oil_level: String = "",
    val voltage_1: Double = 0.0,
    val voltage_2: Double = 0.0,
    val voltage_3: Double = 0.0,
    val network_1: Int = 0,
    val network_2: Int = 0,
    val network_3: Int = 0,
    val general: Float = 0f,
    val time: Int= 0


)

enum class SystemState {
    DISABLED,
    STABLE,
    WARNING,
    CRITICAL
}
enum class PowerSource{
    NETWORK,
    GENERATOR,
    NOTHING
}
enum class Mode{
    AUTO,
    MANUAL
}
enum class ButtonState{
    GRAY,
    GREEN,
    RED
}

enum class OilState{
    OK,
    WARNING,
    CRITICAL
}
enum class BatteryState{
    FULL,
    THREE,
    HALF,
    SINGLE
}
enum class TemperatureState{
    PLUS,MINUS
}
