package com.template.energysmart.presentation.screens.authorization.registration

import androidx.lifecycle.viewModelScope
import com.template.energysmart.presentation.base.BaseViewModel
import com.template.energysmart.presentation.screens.authorization.AuthorizationInteractor
import com.template.energysmart.presentation.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ConfirmCodeViewModel @Inject constructor(private val interactor: AuthorizationInteractor) : BaseViewModel() {
    private val _navigation= MutableSharedFlow<Boolean>()
    val navigation=_navigation.asSharedFlow()
    private val _loading= MutableStateFlow(false)
    val loading=_loading.asStateFlow()
    private val _error= MutableStateFlow("")
    val error=_error.asStateFlow()
    private val _enabled= MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()
    private val _code= MutableStateFlow("")
    val code=_code.asStateFlow()

    init {
        interactor.scope=viewModelScope
        start(viewModelScope) {
            interactor.confirmCode.collect {
                when (it) {
                    is ResponseState.Loading -> {
                        _loading.value = it.isLoading
                        _enabled.value=false
                    }
                    is ResponseState.Success-> _navigation.emit(true)
                    is ResponseState.Error->{
                        _error.value=it.throwable.message?:""
                        _enabled.value=true
                    }
                }
            }
        }
    }
    fun checkFieldsState(value:String){
        _code.value=value
        _enabled.value = value.isNotEmpty()
    }
    fun disableButton(){
        _enabled.value=false
    }

    fun confirmCode(phone:String,type:String?)=interactor.confirmCode(phone,code = code.value,type?:"")

    fun sendNewCode(phone:String,type:String?)=interactor.confirmNumber(phone, type?:"")


}