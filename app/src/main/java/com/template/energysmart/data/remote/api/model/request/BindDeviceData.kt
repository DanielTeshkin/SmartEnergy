package com.template.energysmart.data.remote.api.model.request

import com.google.gson.annotations.SerializedName

data class BindDeviceData(
    @SerializedName("device_id") val id:String="",
    @SerializedName("password") val password:String=""

)
