package com.template.energysmart.data.remote.api.model.request

import com.google.gson.annotations.SerializedName

data class UnBindRequest (
    @SerializedName("user_id") val user_id:String?
        )