package com.template.energysmart.presentation.screens.settings

import android.media.audiofx.Equalizer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.energysmart.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SettingsViewModel @Inject constructor( private val settingsInteractor: SettingsInteractor) : BaseViewModel() {
     private val _ui= MutableStateFlow(SettingsViewState())
      val ui=_ui.asStateFlow()
    private val controller=SettingsController()
    init {
        settingsInteractor.apply {
            scope = viewModelScope
            handleState()
            start(viewModelScope) {
                    state.collect {
                        Log.i("param", (it.settingsModel.preventiveStart!=null).toString())
                        Log.i("error", it.error)
                        _ui.value = controller.getStartViewState(it)
                    }
                }
        }
    }
    fun update()=settingsInteractor.update(controller.getUpdateModel())

    fun handleEvent(event: SettingsViewEvent){
       _ui.value= controller.reduceEvent(event)
    }
}