package com.template.energysmart.presentation.screens.notifications

import com.template.energysmart.data.remote.api.model.response.Status
import com.template.energysmart.domain.repositories.NotificationsRepository
import com.template.energysmart.presentation.base.BaseInteractor
import com.template.energysmart.presentation.state.ResponseState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NotificationInteractor @Inject constructor(private val repository: NotificationsRepository):BaseInteractor() {
    private val _loading= MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    private val _error= MutableStateFlow("")
    val error = _error.asStateFlow()
    private val _status= MutableStateFlow(false)
     val  status=_status.asStateFlow()

    fun clickOnOk(id:String) = repository.clickOnOk(id).handleResult({
        _loading.value=it },
        {
            _status.value=true
        },{
            _error.value=it.message?:""
        }
        )
}