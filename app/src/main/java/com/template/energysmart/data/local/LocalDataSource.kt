package com.template.energysmart.data.local

import android.content.Context
import com.template.energysmart.data.AppPreferences
import com.template.energysmart.data.remote.api.model.response.AuthorizationData
import com.template.energysmart.data.remote.api.model.response.Parameter
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val context: Context)  {

    fun getToken()= AppPreferences.getSharedPreferences(context).getString("token", "")
    fun getRefresh()=AppPreferences.getSharedPreferences(context).getString(
        "refresh",
        ""
    )
    fun saveAuthorizationData(item:AuthorizationData){
        AppPreferences.getSharedPreferences(context).edit().putString("token", item.access)
            .putString("refresh", item.refresh)
            .apply()
    }
    fun getParameter(): Parameter? = null
    fun saveParameter(parameter: Parameter){

    }
    fun rebirthToken()= AppPreferences.getSharedPreferences(context).edit().putString("token", "").apply()

    fun getDeviceId():String=AppPreferences.getSharedPreferences(context).getString("device", "")?:""

    fun saveDevice(id:String)=AppPreferences.getSharedPreferences(context).edit().putString("device", id).apply()
}