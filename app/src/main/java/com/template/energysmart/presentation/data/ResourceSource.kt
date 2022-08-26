package com.template.energysmart.presentation.data

import com.template.energysmart.R
import com.template.energysmart.domain.model.Mode
import com.template.energysmart.domain.model.PhasesStateModel
import com.template.energysmart.domain.model.PowerSource
import com.template.energysmart.domain.model.SystemState

class ResourceSource {
    val home= mutableMapOf(SystemState.DISABLED to R.drawable.ic__home_gray,
        SystemState.STABLE to R.drawable.ic_home_green,
        SystemState.WARNING to R.drawable.ic_home_yellow,
        SystemState.CRITICAL to R.drawable.ic_home_red
        )
    val network= mutableMapOf(SystemState.DISABLED to R.drawable.line_electro,
        SystemState.STABLE to R.drawable.line_electro_city_green,
        SystemState.WARNING to R.drawable.line_electro_city_yellow,
        SystemState.CRITICAL to R.drawable.line_electro_city_red
    )
    val generator =mutableMapOf(SystemState.DISABLED to R.drawable.generator_off,
        SystemState.STABLE to R.drawable.generator_green,
        SystemState.WARNING to R.drawable.generator_yellow,
        SystemState.CRITICAL to R.drawable.generator_red
    )
    val auto = mutableMapOf(Mode.AUTO to R.drawable.auto_button,Mode.MANUAL to R.drawable.auto_button_gray)
    val handle= mutableMapOf(Mode.AUTO to R.drawable.hand_button_green,Mode.MANUAL to R.drawable.hand_button_gray)
    val repositoryPhases= mutableMapOf(PowerSource.NETWORK to PhasesResourceNetwork())
    inner class PhasesResourceNetwork(){
        val phases= mutableMapOf(PhasesStateModel(
            phase_state_1 = SystemState.DISABLED,
            phase_state_2 = SystemState.DISABLED,
            phase_state_3 = SystemState.DISABLED
        ) to R.drawable.phases_line_gray,
            PhasesStateModel(
                phase_state_1 = SystemState.STABLE,
                phase_state_2 = SystemState.STABLE,
                phase_state_3 = SystemState.STABLE
            ) to R.drawable.phases_line_green,
            PhasesStateModel(
                phase_state_1 = SystemState.CRITICAL,
                phase_state_2 = SystemState.CRITICAL,
                phase_state_3 = SystemState.CRITICAL
            ) to R.drawable.phases_line_red,
            PhasesStateModel(
                phase_state_1 = SystemState.WARNING,
                phase_state_2 = SystemState.WARNING,
                phase_state_3 = SystemState.WARNING
            ) to R.drawable.phases_line_yellow,
            PhasesStateModel(
                phase_state_1 = SystemState.STABLE,
                phase_state_2 = SystemState.STABLE,
                phase_state_3 = SystemState.CRITICAL
            ) to R.drawable.phases_line_3_red_green,
            PhasesStateModel(
                phase_state_1 = SystemState.DISABLED,
                phase_state_2 = SystemState.DISABLED,
                phase_state_3 = SystemState.CRITICAL
            ) to R.drawable.phases_line_3_red_gray,
            PhasesStateModel(
                phase_state_1 = SystemState.WARNING,
                phase_state_2 = SystemState.WARNING,
                phase_state_3 = SystemState.CRITICAL
            ) to R.drawable.phases_line_3_red_yellow,
            PhasesStateModel(
                phase_state_1 = SystemState.WARNING,
                phase_state_2 = SystemState.CRITICAL,
                phase_state_3 = SystemState.WARNING
            ) to R.drawable.phases_line_2_red_yellow,
            PhasesStateModel(
                phase_state_1 = SystemState.DISABLED,
                phase_state_2 = SystemState.CRITICAL,
                phase_state_3 = SystemState.DISABLED
            ) to R.drawable.phases_line_2_red_gray,
            PhasesStateModel(
                phase_state_1 = SystemState.STABLE,
                phase_state_2 = SystemState.CRITICAL,
                phase_state_3 = SystemState.STABLE
            ) to R.drawable.phases_line_2_red_green,
            PhasesStateModel(
                phase_state_1 = SystemState.CRITICAL,
                phase_state_2 = SystemState.STABLE,
                phase_state_3 = SystemState.STABLE
            ) to R.drawable.phases_line_1_red_green,
            PhasesStateModel(
                phase_state_1 = SystemState.CRITICAL,
                phase_state_2 = SystemState.DISABLED,
                phase_state_3 = SystemState.DISABLED
            ) to R.drawable.phases_line_1_red_gray,
            PhasesStateModel(
                phase_state_1 = SystemState.CRITICAL,
                phase_state_2 = SystemState.WARNING,
                phase_state_3 = SystemState.WARNING
            ) to R.drawable.phases_line_1_red_yellow,


            )
    }
    inner class PhasesResourceGenerator(){

    }

}
