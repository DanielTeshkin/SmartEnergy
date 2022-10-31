package com.template.energysmart.data.remote.api.model.response

import com.google.gson.annotations.SerializedName

data class Device(
    @SerializedName("id") val id : String ="",
    @SerializedName("date_created") val date_created : String="",
    @SerializedName("date_update") val date_update : String="",
    @SerializedName("comment") val comment : String="",
    @SerializedName("command") val command : String ="",
    @SerializedName("mode") val mode : String ="AUTO",
    @SerializedName("users") val users : List<User> = listOf()
)
data class User(
    @SerializedName("id") val id:String ="",
    @SerializedName("username") val username:String ="",
    @SerializedName("first_name") val first_name:String? ="",

    )