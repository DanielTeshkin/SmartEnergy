package com.template.energysmart.presentation.screens.notifications

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.template.energysmart.R
import com.template.energysmart.presentation.theme.ErrorText
import com.template.energysmart.presentation.theme.RedLight
import com.template.energysmart.presentation.theme.SimpleText
import com.template.energysmart.presentation.theme.YellowLemon
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationViewState(
    val image:Int?=R.drawable.ic_oil,
    val title:String="",
    val description:String="",
    val instruction:String?="",
    val textMode:String?=null,
    val theme:ThemeViewState?= ThemeViewState(),
    val buttonFirstEvent: NotificationsViewEvent?=null,
    val buttonSecondEvent: NotificationsViewEvent?=null
    ):Parcelable
@Parcelize
data class ThemeViewState(
    val background: Color= YellowLemon,
    val generalText:Color= SimpleText,
    val buttonFirst: Int?=null,
    val buttonSecond:Int?= R.drawable.ic_close_red
):Parcelable

data class AlertNotificationState(
    val id:String="",
    val background: Color= RedLight,
    val lineColor: Color= ErrorText,
    val imageClose: Int = R.drawable.ic_cancel_alert_red,
    val title:String="",
    val description:String=""
)