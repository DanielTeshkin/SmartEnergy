package com.template.energysmart.presentation.screens.main.models


import androidx.compose.ui.graphics.Color
import com.template.energysmart.R
import com.template.energysmart.presentation.theme.MainGrayColor
import java.lang.Error

data class MainViewState(
    val electricNetworkImage: Int= R.drawable.line_electro,
    val generatorImage: Int=R.drawable.generator_off,
    val phasesImage:Int=R.drawable.phases_line_gray,
    val imageStateGenerator:Int?=R.drawable.start_active,
    val stateGenerator:Int=0,
    val stationImage:Int=R.drawable.ic_ev_station,
    val oilImage:Int=R.drawable.ic_oil,
    val timeImage:Int=R.drawable.ic_union,
    val stationText:String="",
    val timeText:String="",
    val oilText:String="",
    val temperature: Int =0,
    val isCheckedCommand:Boolean=false,
    val commandButton:Int=0,
    val manualButton: Int=R.drawable.hand_button_gray,
    val autoButtonImage: Int=R.drawable.hand_button_green,
    val autoButtonIsEnabled:Boolean=true,
    val handButtonIsEnabled:Boolean=false,
    val commandButtonIsEnabled:Boolean=false,
    val commandButtonEnableImage:Int=0,
    val homeImage:Int=R.drawable.ic_home_green,
    val phase_vol_1: Int =0,
    val phase_vol_2: Int =0,
    val phase_vol_3: Int =0,
    val loading:Boolean=false,
    val error:String="",
    val isManual:Boolean=false,
    val isAuto:Boolean=true,




    )
class MainUiConfigurationBuilder(){
    var backgroundColor: Color = MainGrayColor
    val electricNetworkImage= R.drawable.line_electro
    val electricNetworkColor:Color= MainGrayColor
    val phasesImage=R.drawable.phases_line_gray
    val generatorImage=R.drawable.generator_off
    val imageStateGenerator=R.drawable.start_active
   val imageFirst=R.drawable.ic_ev_station

    fun backgroundColor(backgroundColor: Color) : MainUiConfigurationBuilder{
        this.backgroundColor=backgroundColor
        return this
    }

}
