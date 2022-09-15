package com.template.energysmart.data.remote.api.model.request

import com.google.gson.annotations.SerializedName

data class CommandRequest(
    @SerializedName("device_id") val device_id:String,
    @SerializedName("command") val command: Command,

    )


enum class Command {
    START,
    STOP,
    AUTO,
    MANUAL
}
