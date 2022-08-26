package com.template.energysmart.presentation.screens.main.models


import com.template.energysmart.presentation.screens.main.MainViewEvent
import com.template.energysmart.presentation.state.DataMain
import com.template.energysmart.presentation.state.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class MainUiController(){
private val _ui= MutableStateFlow(MainViewState())
   private val ui=_ui.asStateFlow()



   fun draw(uiState: MainState){
      when(uiState){
           is MainState.Loading ->  _ui.value=MainViewState()
           is MainState.Error->  _ui.value=MainViewState()
       //   is MainState.DataState->{setConfig(uiState.data)}
       }

   }
    fun getState()=ui

    private fun setConfig(data:DataMain){
     //  _ui.value= MainViewState(
         //    phase_vol_1 = data.metric?.input1.toString(),
        //     phase_vol_2 = data.metric?.input2.toString(),
         //    phase_vol_3 = data.metric?.input3.toString(),
         //  temperature = data.metric?.temperature_air.toString(),
         //  timeText = data.parameter?.reset_oil_change.toString()



       //)
    }

    fun reduce(mainViewEvent: MainViewEvent){
        when(mainViewEvent){
            is MainViewEvent.ChangeModeIntent->{
                //_ui.value= MainViewState(a)
            }
            is MainViewEvent.SendCommandIntent->{
              // if (ui.value.commandButtonIsChecked)
            }
        }
    }

}