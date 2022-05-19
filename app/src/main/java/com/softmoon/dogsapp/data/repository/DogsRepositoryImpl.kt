package com.softmoon.dogsapp.data.repository

import com.softmoon.dogsapp.data.local.daos.DogsDao
import com.softmoon.dogsapp.data.remote.services.DogsApi
import com.softmoon.dogsapp.data.repository.mappers.DogsDomainMapper
import com.softmoon.dogsapp.data.repository.mappers.DogsEntityMapper
import com.softmoon.dogsapp.domain.model.Dog
import com.softmoon.dogsapp.domain.repository.DogsRepository
import com.softmoon.dogsapp.utils.DispatcherProvider
import com.softmoon.dogsapp.utils.Resource
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(
    private val api: DogsApi,
    private val dao: DogsDao,
    private val dispatcher: DispatcherProvider,
    private val dogsDomainMapper: DogsDomainMapper,
    private val dogsEntityMapper: DogsEntityMapper
): SafeApiRequest(), DogsRepository {
    override suspend fun getDogs(): Flow<Resource<List<Dog>>> = flow {
        val count = dao.getCount()

        if (count <= 0) {
            when (val response = safeApiCall { api.getDogs() }) {
                is Resource.Failure ->
                    emit(response)
                is Resource.Success -> {
                    val entityEntries = response
                        .value
                        .map(dogsEntityMapper::mapTo)

                    dao.upsert(entityEntries)

                    dao.getAll().collect { dogsLocal ->
                        emit(
                            Resource
                                .Success(
                                    dogsLocal
                                        .map(dogsDomainMapper::mapTo)
                                )
                        )
                    }
                }
            }
        } else {
            dao.getAll().collect { dogsLocal ->
                emit(
                    Resource
                        .Success(
                            dogsLocal
                                .map(dogsDomainMapper::mapTo)
                        )
                )
            }
        }

    }.flowOn(dispatcher.io)

}