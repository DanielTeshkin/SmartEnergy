package com.template.energysmart.presentation.data

import com.template.energysmart.R
import com.template.energysmart.domain.model.FormatNotification
import com.template.energysmart.domain.model.ImageType
import com.template.energysmart.presentation.screens.notifications.NotificationsViewEvent
import com.template.energysmart.presentation.screens.notifications.ThemeViewState
import com.template.energysmart.presentation.theme.*

class NotificationsResource(id: String) {

    val themeMap = mutableMapOf(FormatNotification.SEND_COMMAND_NOTIFY to ThemeViewState(
        background = GreenNotify,
        buttonFirst = R.drawable.ic_ok_green,
        buttonSecond = R.drawable.ic_cancel_green
    ),FormatNotification.SEND_COMMAND_PROBLEM to ThemeViewState(
        background = RedLight,
        generalText = ErrorText,
        buttonFirst = R.drawable.ic_red_ok,
        buttonSecond = R.drawable.ic_cancel_red
    ),
        FormatNotification.SEND_COMMAND_ERROR to ThemeViewState(
                background = RedLight,
            generalText = ErrorText,
            buttonFirst = R.drawable.ic_instruction_green,
            buttonSecond = R.drawable.ic_ok_red
    ),
        FormatNotification.INSTRUCTION_NOTIFY to ThemeViewState(
                background = YellowLemon,
            buttonFirst = R.drawable.ic_instruction_orange,
            buttonSecond = R.drawable.ic_ok_yellow
    ),
        FormatNotification.INSTRUCTION_DANGER to ThemeViewState(
            background = RedLight,
            generalText = ErrorText,
            buttonFirst = R.drawable.ic_red_instructon,
            buttonSecond = R.drawable.ic_ok_red
        ),
        FormatNotification.ONLY_CLOSE to ThemeViewState(
            background = RedLight,
            generalText = ErrorText,
            buttonFirst= null,
           buttonSecond = R.drawable.ic_close_red
        ),
                FormatNotification.ONLY_OK to ThemeViewState(
                background = YellowLemon,
                    buttonFirst = null,
                    buttonSecond = R.drawable.ic_ok_yellow
                )
    )

    val imageMap = mutableMapOf(
        ImageType.CITY_OFF to R.drawable.network_crash,
        ImageType.LAUNCH_ERROR_GENERATOR to R.drawable.ic_launch_error_generator,
        ImageType.LAUNCH_ERROR_COLD to R.drawable.ic_launch_error_cold,
        ImageType.LAUNCH_ERROR_AUTOMATION to R.drawable.ic_launch_error_automation,
        ImageType.OVERLOAD to R.drawable.ic_overload,
        ImageType.TANK to R.drawable.ic_tank,
        ImageType.GENERATOR_STOP_LOW to R.drawable.generqtor_stop_low,
        ImageType.GENERATOR_STOP_HIGH to R.drawable.generator_stop_high,
        ImageType.OIL to R.drawable.ic_oil,
        ImageType.BALANCE to R.drawable.ic_balance,
        ImageType.BATTERY_CRUSH to R.drawable.ic_battery_crash,
        ImageType.BATTERY to R.drawable.ic_battery,
        ImageType.GSM to R.drawable.ic_gsm,
        ImageType.BUTTON_RED to R.drawable.ic_button_red,
        ImageType.GENERATOR_CRASH to R.drawable.ic_generator_crash

    )


        val eventMap = mutableMapOf( FormatNotification.SEND_COMMAND_NOTIFY
                to EventButtonModel(NotificationsViewEvent.ClickOnOkEvent(id),
            NotificationsViewEvent.IgnoreNotificationEvent),
            FormatNotification.SEND_COMMAND_PROBLEM to EventButtonModel(NotificationsViewEvent.ClickOnOkEvent(id),
            NotificationsViewEvent.IgnoreNotificationEvent),
            FormatNotification.SEND_COMMAND_ERROR to EventButtonModel(
                NotificationsViewEvent.NavigationToInstructionEvent,NotificationsViewEvent.ClickOnOkEvent(id)),
            FormatNotification.INSTRUCTION_NOTIFY to EventButtonModel(
                NotificationsViewEvent.NavigationToInstructionEvent,NotificationsViewEvent.ClickOnOkEvent(id)),
            FormatNotification.INSTRUCTION_DANGER to EventButtonModel(
                NotificationsViewEvent.NavigationToInstructionEvent,NotificationsViewEvent.ClickOnOkEvent(id)),
            FormatNotification.ONLY_OK to EventButtonModel(
                null,NotificationsViewEvent.ClickOnOkEvent(id)),
            FormatNotification.ONLY_CLOSE to EventButtonModel(
                null,NotificationsViewEvent.ClickOnOkEvent(id)),

        )


}

data class EventButtonModel(
    val first: NotificationsViewEvent?,
    val second:NotificationsViewEvent
)



