package com.template.energysmart.domain.model

import android.content.res.Resources
import androidx.compose.runtime.Composable
import com.template.energysmart.presentation.screens.main.MainInteractor
import java.util.*
import kotlin.collections.HashMap

data class NotificationModel(
   val id:String,
   val data:DataNotification
)


data class DataNotification(
    val  format:FormatNotification=FormatNotification.ALERT_DANGER,
    val title:String="",
    val description:String="",
    val instruction:String?=null,
    val image:ImageType?=null

    )

enum class FormatNotification{
    ALERT_NOTIFY,
    ALERT_DANGER,
    ONLY_CLOSE,
    SEND_COMMAND_NOTIFY,
    SEND_COMMAND_PROBLEM,
    SEND_COMMAND_ERROR,
    ONLY_OK,
    INSTRUCTION_NOTIFY,
    INSTRUCTION_DANGER,
}

enum class ImageType{
    CITY_OFF,
    LAUNCH_ERROR_GENERATOR,
    LAUNCH_ERROR_COLD,
    LAUNCH_ERROR_AUTOMATION,
    OVERLOAD,
    OIL,
    TANK,
    BATTERY,
    BATTERY_CRUSH,
    BUTTON_RED,
    GENERATOR_STOP_HIGH,
    GENERATOR_STOP_LOW,
    GENERATOR_CRASH,
    BALANCE,
    GSM
}


