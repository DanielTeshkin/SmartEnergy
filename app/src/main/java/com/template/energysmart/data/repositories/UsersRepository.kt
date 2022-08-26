package com.template.energysmart.data.repositories

import android.content.Context
import com.template.energysmart.data.local.LocalDataSource
import com.template.energysmart.data.remote.RemoteDataSource
import com.template.energysmart.data.remote.api.model.request.ConfirmCodeRequestData
import com.template.energysmart.data.remote.api.model.request.ConfirmNumberRequestData
import com.template.energysmart.data.remote.api.model.request.SignUpRequestData
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
    fun confirmCode(phone: String,code:String) = flow {
        val result=handleOrDefault(ConfirmCodeResponseData()){remote.confirmCode(
            ConfirmCodeRequestData(phone, code)
        )}
        emit(result)
    }
    fun createPassword(username: String,password:String)= flow{
        val result=handleOrDefault(AuthorizationData()){remote.registration(SignUpRequestData(username, password))}
        emit(result)
    }


    fun login(username: String, password: String) = flow {
        val result =
            handleOrDefault(AuthorizationData()) { remote.authorization(username, password) }
        emit(result)
    }

    fun saveAuthorizationData(data: AuthorizationData)=local.saveAuthorizationData(data)

}