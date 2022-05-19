package com.softmoon.dogsapp.di

import com.softmoon.dogsapp.data.repository.DogsRepositoryImpl
import com.softmoon.dogsapp.domain.repository.DogsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindDogsRepository(impl: DogsRepositoryImpl): DogsRepository
}