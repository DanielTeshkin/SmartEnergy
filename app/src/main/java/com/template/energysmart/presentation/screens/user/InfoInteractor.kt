package com.template.energysmart.presentation.screens.user

import com.template.energysmart.data.remote.api.model.request.UserInfoRequest
import com.template.energysmart.data.repositories.BaseRepository
import com.template.energysmart.data.repositories.UsersRepository
import com.template.energysmart.presentation.base.BaseInteractor
import com.template.energysmart.presentation.state.ResponseState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class InfoInteractor @Inject constructor(private val usersRepository: UsersRepository) :BaseInteractor(){
    private val _ui= MutableSharedFlow<ResponseState<*>>()
    val ui=_ui.asSharedFlow()
    fun update(request: UserInfoRequest)=usersRepository.userInfoUpdate(request).handleResult(_ui)
}