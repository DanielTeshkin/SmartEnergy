package com.template.energysmart.presentation.screens.main.models


import androidx.compose.ui.graphics.Color
import com.template.energysmart.R
import com.template.energysmart.domain.model.PowerSource
import com.template.energysmart.presentation.theme.GreenEclipse
import com.template.energysmart.presentation.theme.MainGrayColor
import java.lang.Error

data class MainViewState(
    val electricNetworkImage: Int= R.drawable.line_electro,
    val generatorImage: Int=R.drawable.generator_off,
    val pNetworkImageFirst:Int=R.drawable.point_network_green,
    val pNetworkImageSecond:Int=R.drawable.point_network_green,
    val pNetworkImageThird:Int=R.drawable.point_network_green,
    val phaseFirstImage:Int=R.drawable.phase_generator_3_green,
    val phaseImageSecond:Int=R.drawable.phase_generator_2_green,
    val phaseImageThird:Int=R.drawable.phase_generator_1_green,
    val phasePointFirst:Int=R.drawable.round_phase_3,
    val phasePointSecond:Int=R.drawable.round_phase_2,
    val phasePointThird:Int=R.drawable.round_phase_1,
    val stateGenerator:Int=0,
    val stationImage:Int=R.drawable.ic_ev_station,
    val oilImage:Int=R.drawable.ic_vector,
    val timeImage:Int=R.drawable.ic_union,
    val stationText:String="",
    val timeText:String="",
    val oilText:String="",
    val temperature: Int =0,
    val isCheckedCommand:Boolean=false,
    var commandButton:Int=R.drawable.is_start_gray,
    var manualButton: Int=R.drawable.hand_button_gray,
    var autoButton: Int=R.drawable.auto_button,
    var autoButtonIsEnabled:Boolean=true,
    var handButtonIsEnabled:Boolean=false,
    var commandButtonIsEnabled:Boolean=false,
    val commandButtonImage:Int=R.drawable.ic_start_test,
    val homeImage:Int=R.drawable.ic_home_green,
    val phase_vol_1: String ="0",
    val phase_vol_2: String ="0",
    val phase_vol_3: String ="0",
    val eclipseColor: Color= GreenEclipse,
    val source:PowerSource= PowerSource.NOTHING,
    val imageOil:Int =R.drawable.ic_vector,
    val batteryImage:Int=R.drawable.ic_full_battery,
    val cold:Boolean=true,

    val network_1: Int =0,
    val network_2: Int =0,
    val network_3: Int =0,
    val general:Float=0f,
    val time:Int=0

    )

