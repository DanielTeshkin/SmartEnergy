package com.template.energysmart.di

import android.content.Context

import com.template.energysmart.BuildConfig
import com.template.energysmart.data.remote.MyInterceptor

import com.template.energysmart.data.remote.RemoteDataSource
import com.template.energysmart.data.remote.api.ServerApi
import com.template.energysmart.data.repositories.DevicesRepositoryIml
import com.template.energysmart.data.repositories.NotificationRepositoryImpl
import com.template.energysmart.data.repositories.SettingsRepositoryImpl
import com.template.energysmart.domain.repositories.DevicesRepository
import com.template.energysmart.domain.repositories.NotificationsRepository
import com.template.energysmart.domain.repositories.SettingsRepository
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(com.template.energysmart.BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()


    @Provides
    fun provideHttpClient(@ApplicationContext context: Context, interceptor: MyInterceptor):OkHttpClient  {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY )
        return  OkHttpClient.Builder()
            .addInterceptor(interceptor).
            addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()}

    @Singleton
    @Provides
    fun provideServerApi(retrofit: Retrofit): ServerApi =
        retrofit.create(ServerApi::class.java)


    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context


}

@Module
@InstallIn(SingletonComponent::class)
interface BindRepositoriesModule{
    @Binds
    fun provideDevicesRepository(devicesRepositoryIml: DevicesRepositoryIml):DevicesRepository
    @Binds
    fun provideNotificationsRepository(notificationRepositoryImpl: NotificationRepositoryImpl):NotificationsRepository
    @Binds
    fun provideSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository

}