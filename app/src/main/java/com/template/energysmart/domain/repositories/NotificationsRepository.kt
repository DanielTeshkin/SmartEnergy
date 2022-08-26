package com.template.energysmart.domain.repositories

import com.template.energysmart.data.remote.api.model.response.Notification
import com.template.energysmart.data.remote.api.model.response.Status

interface NotificationsRepository {
    suspend fun getNotificationList():List<Notification>
    suspend fun clickOnOk(id:String):Status
}