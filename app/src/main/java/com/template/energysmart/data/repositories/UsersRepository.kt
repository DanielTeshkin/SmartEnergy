package com.template.energysmart.data.repositories

import android.content.Context
import com.template.energysmart.data.local.LocalDataSource
import com.template.energysmart.data.remote.RemoteDataSource
import com.template.energysmart.data.remote.api.base.BaseApiResponse
import com.template.energysmart.data.remote.api.model.request.*
import com.template.energysmart.data.remote.api.model.response.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UsersRepository @Inject constructor(private val remote: RemoteDataSource,private val local: LocalDataSource): BaseRepository() {

    fun confirmNumber(phone:String)= flow{
        val result=handleOrDefault(ConfirmNumberResponseData()){remote.confirmNumber(
            ConfirmNumberRequestData(phone)
        ) }
        emit(result)
    }
    fun confirmNumberForReset(phone:String)= flow{
        val result=handleOrDefault(ConfirmNumberResponseData()){remote.confirmNumberForReset(
            ConfirmNumberRequestData(phone)
        ) }
        emit(result)
    }

    fun confirmCode(phone: String,code:String) = flow {
        val result=handleOrDefault(ConfirmCodeResponseData()){remote.confirmCode(
            ConfirmCodeRequestData(phone, code)
        )}
        emit(result)
    }
    fun confirmCodeReset(phone: String,code:String) = flow {
        val result=handleOrDefault(ConfirmCodeResponseData()){remote.confirmCodeForReset(
            ConfirmCodeRequestData(phone, code)
        )}
        emit(result)
    }
    fun createPassword(username: String,password:String)= flow{
        val result=handleOrDefault(AuthorizationData()){remote.registration(SignUpRequestData(username, password))}
        emit(result)
    }
    fun resetPassword(username: String,password1:String,password2: String)= flow{
        val result=handleOrDefault(Status()){remote.resetPassword(ResetPasswordRequestData(username, password1, password2))}
        emit(result)
    }
    fun getToken()=local.getToken()


    fun login(username: String, password: String) = flow {
        val result =
            handleOrDefault(AuthorizationData()) { remote.authorization(username, password) }
        emit(result)
    }

    fun userInfoUpdate(request:UserInfoRequest)=  flow {
        val result = handle { remote.updateInfo(request) }
        emit(result)
    }

    fun saveAuthorizationData(data: AuthorizationData)=local.saveAuthorizationData(data)

}