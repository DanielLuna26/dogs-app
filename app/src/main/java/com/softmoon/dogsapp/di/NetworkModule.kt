package com.softmoon.dogsapp.di

import com.softmoon.dogsapp.BuildConfig
import android.content.Context
import com.softmoon.dogsapp.data.remote.NetworkConnectionInterceptor
import com.softmoon.dogsapp.data.remote.services.DogsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkInterceptor(@ApplicationContext context: Context): NetworkConnectionInterceptor =
        NetworkConnectionInterceptor(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(networkConnectionInterceptor: NetworkConnectionInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    val loggingInterceptor = HttpLoggingInterceptor()

                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(loggingInterceptor)
                    addInterceptor(networkConnectionInterceptor)
                } else {
                    addInterceptor(networkConnectionInterceptor)
                }
            }
            .build()

    @Singleton
    @Provides
    fun provideGson(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: GsonConverterFactory) =
        Retrofit
            .Builder()
            .addConverterFactory(gson)
            .client(okHttpClient)
            .baseUrl("https://jsonblob.com/api/")
            .build()

    @Singleton
    @Provides
    fun provideDogsApi(retrofit: Retrofit): DogsApi =
        retrofit.create(DogsApi::class.java)


}