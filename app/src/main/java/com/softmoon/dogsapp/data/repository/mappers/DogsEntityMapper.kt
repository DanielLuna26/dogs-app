package com.softmoon.dogsapp.data.repository.mappers

import com.softmoon.dogsapp.data.local.entity.DogEntity
import com.softmoon.dogsapp.data.remote.response.DogResponse
import javax.inject.Inject

class DogsEntityMapper @Inject constructor(): Mapper<DogResponse, DogEntity> {
    override fun mapTo(type: DogResponse): DogEntity = with(type) {
        DogEntity(
            name = name,
            description = description,
            age = age,
            imgUrl = imgUrl
        )
    }
}