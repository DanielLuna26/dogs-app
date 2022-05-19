package com.softmoon.dogsapp.data.repository

import com.softmoon.dogsapp.utils.NoInternetException
import com.softmoon.dogsapp.utils.Resource
import retrofit2.HttpException

abstract class SafeApiRequest {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) : Resource<T> {
        return try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when(throwable) {
                is HttpException ->
                    Resource
                        .Failure(
                            false,
                            throwable.response()?.code(),
                            throwable.response()?.message()
                        )
                is NoInternetException ->
                    Resource.Failure(true, null, null)
                else ->
                    Resource.Failure(false, null, throwable.message)
            }
        }
    }
}