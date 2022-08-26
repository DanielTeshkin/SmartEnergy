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
class ConfirmNumberViewModel @Inject constructor( private val interactor: AuthorizationInteractor) : BaseViewModel() {
    private val _navigation= MutableSharedFlow<Boolean>()
    val navigation=_navigation.asSharedFlow()
    private val _loading= MutableStateFlow(false)
    val loading=_loading.asStateFlow()
    private val _error= MutableStateFlow("")
    val error=_error.asStateFlow()
    private val _enabled= MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()
    private val _phone=MutableStateFlow("")
   val phone=_phone.asStateFlow()

    init {
        interactor.scope=viewModelScope
        start(viewModelScope) {
            interactor.confirmNumber.collect {
                when (it) {
                    is ResponseState.Loading -> _loading.value=true
                    is ResponseState.Success-> _navigation.emit(true)
                    is ResponseState.Error->_error.value=it.throwable.message?:""
                }
            }
        }
    }
    fun checkFieldsState(value:String){
        _phone.value=value
        _enabled.value = (value.isNotEmpty() and (value.length==11))
    }

    fun confirmNumber()=interactor.confirmNumber(phone.value)
}