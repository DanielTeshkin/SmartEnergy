package com.template.energysmart.presentation.screens.notifications

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class NotificationsViewEvent : Parcelable {
    @Parcelize
    object IgnoreNotificationEvent : NotificationsViewEvent()
    @Parcelize
    object NavigationToInstructionEvent : NotificationsViewEvent()
    @Parcelize
    data class ClickOnOkEvent(val id:String) : NotificationsViewEvent()
}
