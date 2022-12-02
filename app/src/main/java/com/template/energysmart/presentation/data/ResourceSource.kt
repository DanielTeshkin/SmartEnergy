package com.template.energysmart.presentation.data

import androidx.compose.ui.graphics.Color
import com.template.energysmart.R
import com.template.energysmart.data.remote.api.model.request.Command
import com.template.energysmart.domain.model.*
import com.template.energysmart.presentation.theme.*

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

    val  batteryImage= mutableMapOf(
        BatteryState.HALF to R.drawable.ic_half_battery,
        BatteryState.THREE to R.drawable.ic_three_battery,
        BatteryState.FULL to R.drawable.ic_full_battery,
        BatteryState.SINGLE to R.drawable.ic_single_battery
    )
    val oilImage= mutableMapOf(OilState.WARNING to R.drawable.ic_oil_yellow,
       OilState.CRITICAL to R.drawable.ic_oil_redd,
        OilState.OK to R.drawable.ic_vector
        )
    val modeButtons= mutableMapOf(Mode.AUTO to Pair(R.drawable.auto_button,R.drawable.hand_button_gray),
        Mode.MANUAL to Pair(R.drawable.auto_button_gray,R.drawable.hand_button_green))
    val auto = mutableMapOf(Mode.AUTO to R.drawable.auto_button,Mode.MANUAL to R.drawable.auto_button_gray)
    val handle= mutableMapOf(Mode.AUTO to R.drawable.hand_button_green,Mode.MANUAL to R.drawable.hand_button_gray)
    val actualCommand = mutableMapOf(ButtonState.RED to R.drawable.ic_stop_test,
       ButtonState.GREEN to R.drawable.ic_start_test,ButtonState.GRAY to R.drawable.is_start_gray)
    val eclipse= mutableMapOf(SystemState.STABLE to GreenEclipse,SystemState.WARNING to Orange,
        SystemState.CRITICAL to ErrorText,
        SystemState.DISABLED to MainGrayColor
    )
    val stateGenerator= mutableMapOf(mapOf(Pair(Mode.AUTO,Command.START)) to R.drawable.start_test)
    val pointNetworkState= mutableMapOf(SystemState.CRITICAL to R.drawable.point_network_red,
        SystemState.DISABLED to R.drawable.point_network_gray,
        SystemState.STABLE to R.drawable.point_network_green,
        SystemState.WARNING to R.drawable.point_network_yellow)


    val repositoryPhases= mutableMapOf(PowerSource.NETWORK to PhasesResourceNetwork(),PowerSource.GENERATOR to PhasesResourceGenerator())


}
  abstract class PhasesResource(){
      abstract val phaseFirstState : MutableMap<SystemState,Pair<Int,Int>>
      abstract val phaseSecondState : MutableMap<SystemState,Pair<Int,Int>>
      abstract val phaseThirdState : MutableMap<SystemState,Pair<Int,Int>>

  }


 class PhasesResourceNetwork:PhasesResource(){
    override val phaseFirstState= mutableMapOf(SystemState.CRITICAL to Pair(R.drawable.phase_1_red,R.drawable.round_phase_3_red),
        SystemState.WARNING to Pair(R.drawable.phase_1_yellow,R.drawable.round_phase_3_yellow),
        SystemState.STABLE to Pair(R.drawable.phase_1_green ,R.drawable.round_phase_3),
        SystemState.DISABLED to Pair(R.drawable.phase_1_gray, R.drawable.round_phase_3_gray)
    )
    override val phaseSecondState=mutableMapOf(SystemState.CRITICAL to
            Pair(R.drawable.phase_2_red,R.drawable.round_phase_2_red),
        SystemState.WARNING to Pair(R.drawable.phase_2_yellow,R.drawable.round_phase_2_yellow),
        SystemState.STABLE to
                Pair(R.drawable.phase_2_green,R.drawable.round_phase_2),
        SystemState.DISABLED to Pair(R.drawable.phase_2_gray,R.drawable.round_phase_2_gray))
   override val phaseThirdState=mutableMapOf(SystemState.CRITICAL to
            Pair(R.drawable.phase_3_red,R.drawable.round_phase_1_red),
        SystemState.WARNING to Pair(R.drawable.phase_3_yellow,R.drawable.round_phase_1_yellow),
        SystemState.STABLE to
                Pair(R.drawable.phase_3_green,R.drawable.round_phase_1),
        SystemState.DISABLED to Pair(R.drawable.phase_3_gray,R.drawable.round_phase_1_gray))

}
 class PhasesResourceGenerator:PhasesResource(){
  override  val phaseFirstState= mutableMapOf(SystemState.CRITICAL to
            Pair(R.drawable.phase_generator_1_red,R.drawable.round_phase_3_red),
        SystemState.WARNING to Pair(R.drawable.phase_generator_1_yellow,R.drawable.round_phase_3_yellow),
        SystemState.STABLE to
                Pair(R.drawable.phase_generator_1_green,R.drawable.round_phase_3),
        SystemState.DISABLED to Pair(R.drawable.phase_generator_1_gray,R.drawable.round_phase_3_gray))
   override val phaseSecondState= mutableMapOf(SystemState.CRITICAL to
            Pair(R.drawable.phase_generator_2_red,R.drawable.round_phase_2_red),
        SystemState.WARNING to Pair(R.drawable.phase_generator_2_yellow,R.drawable.round_phase_2_yellow),
        SystemState.STABLE to
                Pair(R.drawable.phase_generator_2_green,R.drawable.round_phase_2),
        SystemState.DISABLED to Pair(R.drawable.phase_generator_2_gray,R.drawable.round_phase_2_gray))
  override   val phaseThirdState= mutableMapOf(SystemState.CRITICAL to
            Pair(R.drawable.phase_generator_3_red,R.drawable.round_phase_1_red),
        SystemState.WARNING to Pair(R.drawable.phase_generator_3_yellow,R.drawable.round_phase_1_yellow),
        SystemState.STABLE to
                Pair(R.drawable.phase_generator_3_green,R.drawable.round_phase_1),
        SystemState.DISABLED to Pair(R.drawable.phase_generator_3_gray,R.drawable.round_phase_1_gray))
}
