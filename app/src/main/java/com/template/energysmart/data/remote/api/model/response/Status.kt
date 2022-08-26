package com.template.energysmart.data.remote.api.model.response

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("status") val status: String="OK"
)