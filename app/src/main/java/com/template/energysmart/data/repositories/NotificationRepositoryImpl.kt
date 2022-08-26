package com.template.energysmart.data.repositories

import com.template.energysmart.data.remote.RemoteDataSource
import com.template.energysmart.data.remote.api.model.response.Status
import com.template.energysmart.domain.repositories.NotificationsRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource):BaseRepository(),NotificationsRepository{

   override suspend fun getNotificationList()=handleOrEmptyList { remoteDataSource.getNotifications() }
   override suspend fun clickOnOk(id: String): Status =handleOrDefault(Status()){remoteDataSource.clickOnOk(id)}


}