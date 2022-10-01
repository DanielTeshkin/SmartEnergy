package com.template.energysmart.domain.repositories

import com.template.energysmart.data.remote.api.model.response.Notification
import com.template.energysmart.data.remote.api.model.response.Status
import kotlinx.coroutines.flow.Flow

interface NotificationsRepository {
    suspend fun getNotificationList():List<Notification>
    fun clickOnOk(id:String): Flow<Status>
}