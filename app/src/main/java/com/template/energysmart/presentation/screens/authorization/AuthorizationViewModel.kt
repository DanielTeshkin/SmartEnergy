package com.template.energysmart.presentation.screens.authorization

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.energysmart.data.remote.api.model.response.AuthorizationData
import com.template.energysmart.data.repositories.UsersRepository
import com.template.energysmart.presentation.base.BaseViewModel
import com.template.energysmart.presentation.state.ResponseState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(private val interactor: AuthorizationInteractor):
    BaseViewModel() {
      private val _navigation= MutableSharedFlow<Boolean>()
       val navigation=_navigation.asSharedFlow()
     private val _loading=MutableStateFlow(false)
       val loading=_loading.asStateFlow()
      private val _error= MutableStateFlow("")
              val error=_error.asStateFlow()
    private val _enabled= MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()

    fun signIn(phone:String,password:String)= interactor.signIn(phone, password)


  fun disableButton(){
      _enabled.value=false
  }
    fun checkFieldsState(value:String){
        _enabled.value = value.isNotEmpty()
    }
   init {
       interactor.scope=viewModelScope
       start(viewModelScope) {
           interactor.ui.collect {
               when (it) {
                   is ResponseState.Loading -> {
                       _loading.value=it.isLoading
                       when(it.isLoading){
                           true->_enabled.value=false
                           false -> _enabled.value=true
                       }
                   }
                   is ResponseState.Success-> _navigation.emit(true)
                   is ResponseState.Error->{
                       _enabled.value=true
                       _error.value=it.throwable.message?:""
                   }
               }
           }
       }
   }

   fun remove(){
       onCleared()
   }
}

