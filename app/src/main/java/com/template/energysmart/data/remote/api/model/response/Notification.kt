package com.template.energysmart.data.remote.api.model.response

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("id") val id : String,
    @SerializedName("notification") val notification : NotificationItem,
    @SerializedName("is_ok") val is_ok : Boolean,
    @SerializedName("date_created") val date_created : String,
    @SerializedName("date_updated") val date_updated : String,
    @SerializedName("user") val user : String
)
data class NotificationItem(
    @SerializedName("id") val id : String,
    @SerializedName("type") val type : String,
    @SerializedName("type") val code : Int,

    @SerializedName("date_created") val date_created : String,
    @SerializedName("is_unimportant") val is_unimportant : Boolean,
    @SerializedName("device") val device : String
)
