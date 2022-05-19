package com.softmoon.dogsapp.domain.interators

import com.softmoon.dogsapp.domain.model.Dog
import com.softmoon.dogsapp.domain.repository.DogsRepository
import com.softmoon.dogsapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDogsUseCaseImpl @Inject constructor(
    private val repository: DogsRepository
): GetDogsUseCase {
    override suspend fun invoke(): Flow<Resource<List<Dog>>> =
        repository.getDogs()

}