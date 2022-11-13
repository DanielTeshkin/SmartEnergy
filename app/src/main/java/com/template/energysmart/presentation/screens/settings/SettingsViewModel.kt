package com.template.energysmart.presentation.screens.settings

import android.media.audiofx.Equalizer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.energysmart.data.remote.api.model.request.Command
import com.template.energysmart.data.remote.api.model.response.User
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
    private val _users= MutableStateFlow(mutableListOf<User>())
    val user=_users.asStateFlow()
    val controller=SettingsController()
    init {
        settingsInteractor.apply {
            scope = viewModelScope
            handleState()
            getDevice()
            subscribe(state) { _ui.value = controller.getStartViewState(it) }
            subscribe(users){_users.value.addAll(it)}
        }
    }
    fun update()=settingsInteractor.update(controller.getUpdateModel())
    fun exit()=viewModelScope.launch {  settingsInteractor.exit()}

    private fun state(){

    }
    fun handleEvent(event: SettingsViewEvent){
        _ui.value= controller.reduceEvent(event)
    }
    fun resetOdo(command: Command)=settingsInteractor.resetOdo(command)
    fun unbind(id:String)=settingsInteractor.unbind(id)
    fun removeUser(id: String){
        val user=_users.value.find { it.id==id }
        _users.value.remove(user)
    }
    fun clear()=onCleared()

}