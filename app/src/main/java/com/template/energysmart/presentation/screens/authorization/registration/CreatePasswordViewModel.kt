package com.template.energysmart.presentation.screens.authorization.registration

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.template.energysmart.presentation.base.BaseViewModel
import com.template.energysmart.presentation.screens.authorization.AuthorizationInteractor
import com.template.energysmart.presentation.screens.settings.SettingsInteractor
import com.template.energysmart.presentation.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreatePasswordViewModel @Inject constructor(private val interactor: AuthorizationInteractor) :BaseViewModel() {

        private val _navigation= MutableStateFlow<Boolean>(false)
        val navigation=_navigation.asStateFlow()
        private val _loading= MutableStateFlow(false)
        val loading=_loading.asStateFlow()
        private val _error= MutableStateFlow("")
        val error=_error.asStateFlow()
        private val _enabled= MutableStateFlow(false)
        val enabled=_enabled.asStateFlow()
        private val password= MutableStateFlow("")

        private val passwordRepeat= MutableStateFlow("")


        fun updatePassword(value: String){
            password.value=value
            checkFieldsState()
        }
        fun updateRepeatPassword(value: String){
            passwordRepeat.value=value
            checkFieldsState()
        }
        fun checkFieldsState(){

            _enabled.value = (password.value==passwordRepeat.value)
        }
    init {
        interactor.apply {
            scope = viewModelScope
            start(viewModelScope) {
                subscribe(ui) { state ->
                    handleState(state, {_loading.value=it},{_navigation.value=true},
                        { Log.i("error",it.message.toString())

                            _error.value = it.message?: "Пользователь существует" })
            }
                subscribe(status) { handleState(it, {_loading.value=it},{_navigation.value=true},{
                    Log.i("error",it.message.toString())
                    _error.value = it.message ?: "Пользователь существует"})
        }
            }
        }
    }
    fun disableButton(){
        _enabled.value=false
    }
    fun createPassword(phone:String)=interactor.createPassword(phone, password.value)

    fun resetPassword(phone:String)=interactor.resetPassword(phone, password.value, passwordRepeat.value)
}