package com.template.energysmart.data.remote

import android.content.Context
import com.template.energysmart.data.AppPreferences


import android.content.Intent
import android.util.Log
import com.expostore.api.base.ErrorResponse

import com.google.gson.Gson
import com.template.energysmart.data.local.LocalDataSource
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject



class MyInterceptor @Inject constructor(
    private val context: Context,
    private val remoteDataSource: Lazy<RemoteDataSource>,
    private val localDataSource: Lazy<LocalDataSource>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
       val token=  localDataSource.get().getToken()

        Log.wtf("TOKEN", token)

        val request = chain.request()
            .newBuilder()

        token?.takeIf { it.isNotEmpty() }?.let {
            request.addHeader(AUTHORIZATION, "$BEARER $it")
        }

        val response = chain.proceed(request.build())

        return processResponse(response, chain)
    }

    private fun processResponse(response: Response, chain: Interceptor.Chain): Response {
        if (response.headers(AUTHORIZATION).isNotEmpty() && response.code == 401) {
            triggerRebirth()
        }

        var currentResponse = response
        if (!currentResponse.isSuccessful) {
            when (fetchErrorCode(response.peekBody(Long.MAX_VALUE).string())) {
                TOKEN_OUTDATED -> tryReLogin(chain)?.let {
                    currentResponse = it
                }
            }
        }

        return currentResponse
    }

    private fun tryReLogin(chain: Interceptor.Chain): Response? {
        val newToken = runBlocking {
            remoteDataSource.get().refresh(
                localDataSource.get().getRefresh()?: ""
            ).result
        }
        if (!newToken?.access.isNullOrEmpty()) {
            localDataSource.get().saveAuthorizationData(newToken!!)
            val request = chain.request()
                .newBuilder()
                .header(AUTHORIZATION, "$BEARER ${newToken.access}")
                .build()
            return chain.proceed(request)
        }
        return null
    }

    private fun fetchErrorCode(data: String): String =
        kotlin.runCatching {
            Gson().fromJson(
                data,
                ErrorResponse::class.java
            ).code
        }.getOrDefault("")

    private fun triggerRebirth() {
        localDataSource.get().rebirthToken()
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }
}

private const val AUTHORIZATION = "Authorization"
private const val BEARER = "Bearer"

private const val TOKEN_OUTDATED = "token_not_valid"