package com.softmoon.dogsapp.di

import android.content.Context
import com.softmoon.dogsapp.data.local.DogsDatabase
import com.softmoon.dogsapp.data.local.daos.DogsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideDogsDatabase(@ApplicationContext context: Context): DogsDatabase =
        DogsDatabase(context)

    @Singleton
    @Provides
    fun provideDogsDao(dogsDatabase: DogsDatabase): DogsDao =
        dogsDatabase.dogsDao()
}