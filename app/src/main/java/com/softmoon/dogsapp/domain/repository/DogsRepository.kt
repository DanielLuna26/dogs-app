package com.softmoon.dogsapp.domain.repository

import com.softmoon.dogsapp.domain.model.Dog
import com.softmoon.dogsapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DogsRepository {
    suspend fun getDogs(): Flow<Resource<List<Dog>>>
}