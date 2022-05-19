package com.softmoon.dogsapp.domain.interators

import com.softmoon.dogsapp.domain.model.Dog
import com.softmoon.dogsapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetDogsUseCase {
    suspend operator fun invoke(): Flow<Resource<List<Dog>>>
}