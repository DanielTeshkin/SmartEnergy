package com.template.energysmart.data.remote.api.model.request

import com.google.gson.annotations.SerializedName

data class UserInfoRequest(
    @SerializedName("push_token") val push_token:String,
    @SerializedName("first_name") val first_name:String

)
