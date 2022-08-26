package com.template.energysmart.data.remote.api.base

import com.expostore.api.base.ErrorResponse
import retrofit2.Response


data class ApiResponse<T>(
    override var result: T? = null,
    val message: String = ""
) : BaseApiResponse<T>() {
    companion object {
        fun <T> create(response: Response<T>): BaseApiResponse<T> =
             when (response.isSuccessful) {
                 true-> ApiResponse(response.body())
                 false->ApiErrorResponse.createError(response)
             }

        fun <T> createList(response: Response<List<T>>): BaseApiResponse<List<T>> =
            when (response.isSuccessful) {
                true->   ApiResponse(response.body())
                false -> ApiErrorResponse.createErrorList(response)
        }
    }
}