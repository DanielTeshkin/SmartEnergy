package com.template.energysmart.data.remote


import android.content.Context
import com.expostore.api.request.RefreshTokenRequest
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.template.energysmart.data.remote.api.ServerApi
import com.template.energysmart.data.remote.api.base.ApiException
import com.template.energysmart.data.remote.api.base.ApiResponse
import com.template.energysmart.data.remote.api.base.BaseApiResponse
import com.template.energysmart.data.remote.api.model.request.*
import com.template.energysmart.data.remote.api.model.response.*
import retrofit2.Response
import retrofit2.http.Body
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val serverApi: ServerApi,context: Context) {
    private suspend inline fun <T> processResponse(crossinline action: suspend () -> Response<T>): BaseApiResponse<T> {
        try {
            return ApiResponse.create(action.invoke())
        } catch (throwable: Throwable) {
            throw processError(throwable)
        }
    }

    private suspend inline fun <T> processListResponse(crossinline action: suspend () -> Response<List<T>>): BaseApiResponse<List<T>> {
        try {
            return ApiResponse.createList(action.invoke())
        } catch (throwable: Throwable) {
            throw processError(throwable)
        }
    }

    private fun processError(throwable: Throwable) =
        when (throwable) {
            is UnknownServiceException, is UnknownHostException, is ConnectException ->
                ApiException("Неизвестная ошибка")
            is SocketTimeoutException -> ApiException("Время ожидания истекло")
            is JsonParseException -> ApiException("fff")
            is JsonSyntaxException -> ApiException("ddd")
            else -> throwable
        }


  suspend fun authorization(
        username: String,
        password: String
    ): BaseApiResponse<AuthorizationData> =
        processResponse { serverApi.authorization(SignInRequestData(username, password)) }

     suspend fun refresh(refreshToken: String): BaseApiResponse<AuthorizationData> =
        processResponse { serverApi.refresh(RefreshTokenRequest(refreshToken)) }

    suspend fun confirmNumber(request: ConfirmNumberRequestData): BaseApiResponse<ConfirmNumberResponseData> =
        processResponse { serverApi.confirmNumber(request) }

    suspend fun confirmNumberForReset(request: ConfirmNumberRequestData): BaseApiResponse<ConfirmNumberResponseData>
    = processResponse { serverApi.confirmNumberForReset(request) }

    suspend fun confirmCode(request: ConfirmCodeRequestData): BaseApiResponse<ConfirmCodeResponseData> =
        processResponse { serverApi.confirmCode(request) }

    suspend fun confirmCodeForReset(request: ConfirmCodeRequestData):BaseApiResponse<ConfirmCodeResponseData>
    = processResponse { serverApi.confirmCodeForReset(request) }

    suspend fun registration(request: SignUpRequestData): BaseApiResponse<AuthorizationData> =
        processResponse { serverApi.registration(request) }
    suspend fun resetPassword(request:ResetPasswordRequestData):BaseApiResponse<Status> =
        processResponse { serverApi.resetPassword(request) }


     suspend fun getDevices(): BaseApiResponse<List<Device>> =processListResponse { serverApi.getDevices() }

     suspend fun getDevice(id: String): BaseApiResponse<Device> = processResponse { serverApi.getDevice(id) }

     suspend fun bindDevice( data: BindDeviceData): BaseApiResponse<Status> = processResponse { serverApi.bindDevice(data) }

     suspend fun sendCommand(commandRequest: CommandRequest)  = processResponse { serverApi.sendCommand(commandRequest) }
    suspend fun resetOdo(commandRequest: CommandRequest)  = processResponse { serverApi.resetOdo(commandRequest) }
    suspend fun sendMode( modeRequest: ModeRequest)= processResponse { serverApi.sendMode(modeRequest) }
    suspend fun updateInfo(request:UserInfoRequest) = processResponse { serverApi.updateUserInfo(request) }

     suspend fun getMetrics(id: String): BaseApiResponse<Metric> =processResponse { serverApi.getMetrics(id) }

     suspend fun getParameters(id: String) = processResponse { serverApi.getParameters(id) }

    suspend fun changeParameters(id: String,
         parameter: ParameterRequest) = processResponse { serverApi.changeParameters(id, parameter) }

     suspend fun getNotifications(): BaseApiResponse<List<Notification>> = processListResponse { serverApi.getNotifications() }
    suspend fun clickOnOk(id: String): BaseApiResponse<Status> = processResponse {  serverApi.clickOnOk(id) }
    suspend fun unbind(id: String,user_id:String)= processResponse { serverApi.unBindUser(id,UnBindRequest(user_id)) }
}