package com.template.energysmart.data.remote.api.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class SignInRequestData(
    @JsonProperty("username") val username: String?,
    @JsonProperty("password") val password: String?
)
