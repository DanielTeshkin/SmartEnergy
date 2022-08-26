package com.template.energysmart.data.remote.api.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class ConfirmCodeResponseData(
    @SerializedName("message") val message: String?=""
    )
