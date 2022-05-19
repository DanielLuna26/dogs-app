package com.softmoon.dogsapp.data.repository.mappers

import com.softmoon.dogsapp.data.local.entity.DogEntity
import com.softmoon.dogsapp.domain.model.Dog
import javax.inject.Inject

class DogsDomainMapper @Inject constructor(): Mapper<DogEntity, Dog> {
    override fun mapTo(type: DogEntity): Dog = with(type) {
        Dog(
            id, name, description, age, imgUrl
        )
    }

}