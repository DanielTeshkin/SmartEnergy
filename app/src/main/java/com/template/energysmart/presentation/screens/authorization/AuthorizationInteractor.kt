package com.template.energysmart.presentation.screens.authorization

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.template.energysmart.data.remote.api.model.response.AuthorizationData
import com.template.energysmart.data.remote.api.model.response.ConfirmCodeResponseData
import com.template.energysmart.data.remote.api.model.response.ConfirmNumberResponseData
import com.template.energysmart.data.remote.api.model.response.Status
import com.template.energysmart.data.repositories.UsersRepository
import com.template.energysmart.presentation.base.BaseInteractor
import com.template.energysmart.presentation.state.ResponseState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AuthorizationInteractor @Inject constructor(private val repository: UsersRepository):BaseInteractor() {
    private val _ui=MutableSharedFlow<ResponseState<AuthorizationData>>()
                val ui=_ui.asSharedFlow()
    private val _status=MutableSharedFlow<ResponseState<Status>>()
    val status=_status.asSharedFlow()
    private val _confirmNumber= MutableSharedFlow<ResponseState<ConfirmNumberResponseData>>()
     val confirmNumber=_confirmNumber.asSharedFlow()
    private val _confirmCode= MutableSharedFlow<ResponseState<ConfirmCodeResponseData>>()
    val confirmCode=_confirmCode.asSharedFlow()
    fun signIn(phone:String,password:String)=repository
        .login(phone, password = password)
        .handleResult(_ui,{
            repository.saveAuthorizationData(it)
        })
    fun confirmNumber(phone: String,type:String)=when(type){
        "reset"-> repository.confirmNumberForReset(phone).handleResult(_confirmNumber)
         else ->  repository.confirmNumber(phone).handleResult(_confirmNumber)
    }

    fun confirmCode(phone: String,code:String,type:String)=when(type){
        "reset"-> repository.confirmCodeReset(phone, code).handleResult(_confirmCode)
        else->repository.confirmCode(phone,code).handleResult(_confirmCode)

    }
    fun createPassword(phone: String,password: String)=repository.createPassword(phone, password).handleResult(_ui,{
        repository.saveAuthorizationData(it)
    })
    fun resetPassword(phone: String,password1: String,password2: String)=
        repository.resetPassword(phone, password1,password2).handleResult(_status)

}