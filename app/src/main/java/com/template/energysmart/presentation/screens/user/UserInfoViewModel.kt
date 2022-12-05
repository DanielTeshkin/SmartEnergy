package com.template.energysmart.presentation.screens.user

import androidx.lifecycle.viewModelScope
import com.template.energysmart.data.remote.api.model.request.UserInfoRequest
import com.template.energysmart.data.repositories.UsersRepository
import com.template.energysmart.presentation.base.BaseViewModel
import com.template.energysmart.presentation.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(private val interactor: InfoInteractor):BaseViewModel() {
    private val _loading= MutableStateFlow(false)
    val loading = _loading.asStateFlow()
   private val _error= MutableStateFlow("")
    val error=_error.asStateFlow()
    private val _navigation= MutableStateFlow(false)
    val navigation=_navigation.asStateFlow()
    private val name= MutableStateFlow("")
    private val pushToken= MutableStateFlow("")
    private val _enabled= MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()


    init {
      with(interactor){
          scope=viewModelScope
          subscribe(ui){
              when(it){
                  is ResponseState.Loading-> _loading.value=it.isLoading
                  is ResponseState.Error->_error.value=it.throwable.message?:""
                  is ResponseState.Success->_navigation.value=true
              }
          }
      }
    }

    fun changeName(text:String){
        name.value=text
        _enabled.value=name.value.isNotEmpty()
    }
    fun updateUserInfo()=interactor.update(UserInfoRequest(push_token = pushToken.value, first_name = name.value))

}