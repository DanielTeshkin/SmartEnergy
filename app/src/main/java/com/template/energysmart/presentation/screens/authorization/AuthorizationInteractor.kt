package com.template.energysmart.presentation.screens.authorization

import android.content.Context
import androidx.compose.runtime.MutableState
import com.template.energysmart.data.remote.api.model.response.AuthorizationData
import com.template.energysmart.data.remote.api.model.response.ConfirmCodeResponseData
import com.template.energysmart.data.remote.api.model.response.ConfirmNumberResponseData
import com.template.energysmart.data.repositories.UsersRepository
import com.template.energysmart.presentation.base.BaseInteractor
import com.template.energysmart.presentation.state.ResponseState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AuthorizationInteractor @Inject constructor(private val repository: UsersRepository):BaseInteractor() {
    private val _ui=MutableSharedFlow<ResponseState<AuthorizationData>>()
                val ui=_ui.asSharedFlow()
    private val _confirmNumber= MutableSharedFlow<ResponseState<ConfirmNumberResponseData>>()
     val confirmNumber=_confirmNumber.asSharedFlow()
    private val _confirmCode= MutableSharedFlow<ResponseState<ConfirmCodeResponseData>>()
    val confirmCode=_confirmCode.asSharedFlow()
    fun signIn(phone:String,password:String,context: Context)=repository
        .login(phone, password = password)
        .handleResult(_ui,{
            repository.saveAuthorizationData(it)
        })
    fun confirmNumber(phone: String)=repository.confirmNumber(phone).handleResult(_confirmNumber)
    fun confirmCode(phone: String,code:String)=repository.confirmCode(phone,code).handleResult(_confirmCode)
    fun createPassword(phone: String,password: String)=repository.createPassword(phone, password).handleResult(_ui,{
        repository.saveAuthorizationData(it)
    })


}