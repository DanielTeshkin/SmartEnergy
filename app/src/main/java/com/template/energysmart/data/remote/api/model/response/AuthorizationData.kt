package com.template.energysmart.data.remote.api.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class AuthorizationData(
    @SerializedName("refresh") val refresh: String?="",
    @SerializedName("access") val access: String?=""
)
