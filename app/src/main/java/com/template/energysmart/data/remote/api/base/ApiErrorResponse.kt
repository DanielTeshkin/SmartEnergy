package com.template.energysmart.data.remote.api.base

import com.expostore.api.base.ErrorResponse
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import retrofit2.Response


data class ApiErrorResponse<T>(
    @SerializedName("detail")
    val message: String = "",
) : BaseApiResponse<T>() {
    val exception: ApiException
        get() = ApiException(message)
    companion object{
        fun <T> createError(response: Response<T>):BaseApiResponse<T> =
            ApiErrorResponse(gson.fromJson(response.errorBody()?.string() ?: "", ErrorResponse::class.java).detail)
        fun <T> createErrorList(response: Response<List<T>>):BaseApiResponse<List<T>> =
            ApiErrorResponse(gson.fromJson(response.errorBody()?.string() ?: "", ErrorResponse::class.java).detail)

    }
}