package com.template.energysmart.data.remote.api

import com.expostore.api.request.RefreshTokenRequest
import com.template.energysmart.data.remote.api.model.request.*

import com.template.energysmart.data.remote.api.model.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServerApi {


    @POST("/api/sign-in/")
    suspend fun authorization(@Body request: SignInRequestData): Response<AuthorizationData>

    @POST("/api/token/refresh/")
    suspend fun refresh(@Body refresh: RefreshTokenRequest): Response<AuthorizationData>

    @POST("/api/confirm/create/")
    suspend fun confirmNumber(@Body request: ConfirmNumberRequestData): Response<ConfirmNumberResponseData>

    @POST("/api/confirm/confirmed/")
    suspend fun confirmCode(@Body request: ConfirmCodeRequestData): Response<ConfirmCodeResponseData>

    @POST("/api/sign-up/")
    suspend fun registration(@Body request: SignUpRequestData): Response<AuthorizationData>

    @GET("/api/devices/")
    suspend  fun getDevices():Response<List<Device>>

    @GET("/api/devices/{id}/")
    suspend  fun getDevice(@Path("id") id: String):Response<Device>

    @POST("/api/devices/reg_device/")
    suspend fun bindDevice(@Body data: BindDeviceData):Response<Status>

    @POST("/api/devices/command/")
    suspend  fun sendCommand(@Body commandRequest: CommandRequest):Response<Status>

    @POST("/api/devices/mode/")
    suspend fun sendMode(@Body commandRequest: CommandRequest):Response<Status>

    @GET("/api/metric/{id}/")
    suspend  fun getMetrics(@Path ("id") id:String):Response<Metric>

    @GET("/api/parameter/{id}/")
    suspend fun getParameters(@Path("id") id:String):Response<Parameter>

    @POST("/api/parameter/{id}/")
    suspend fun changeParameters(@Path("id") id:String, @Body parameter: ParameterRequest):Response<Parameter>

    @GET("/api/notification/get_list/")
    suspend  fun getNotifications():Response<List<Notification>>

    @POST("/api/notification/push/{id}/click_push_ok/")
    suspend fun clickOnOk(@Path("id") id:String):Response<Status>





}