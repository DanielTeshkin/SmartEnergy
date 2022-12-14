package com.expostore.api.base

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName


data class ErrorResponse(
    @field:SerializedName("message") val detail: String = "",
    @field:SerializedName("code") val code: String = ""
)