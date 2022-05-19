package com.softmoon.dogsapp.di

import com.softmoon.dogsapp.domain.interators.GetDogsUseCase
import com.softmoon.dogsapp.domain.interators.GetDogsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface PresentationModule {

    @Binds
    fun bindGetDogsUseCase(impl: GetDogsUseCaseImpl): GetDogsUseCase
}