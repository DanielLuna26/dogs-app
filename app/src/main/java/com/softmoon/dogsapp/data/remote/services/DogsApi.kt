package com.softmoon.dogsapp.data.remote.services

import com.softmoon.dogsapp.data.remote.response.DogResponse
import retrofit2.http.GET

interface DogsApi {
    @GET("945366962796773376")
    suspend fun getDogs(): List<DogResponse>
}