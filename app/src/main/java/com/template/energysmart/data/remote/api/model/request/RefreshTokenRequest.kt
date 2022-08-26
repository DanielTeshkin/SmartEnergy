package com.expostore.api.request

import com.google.gson.annotations.SerializedName


data class RefreshTokenRequest(
    @field:SerializedName("refresh")
    val refresh: String
)