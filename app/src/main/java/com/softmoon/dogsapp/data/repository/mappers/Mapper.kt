package com.softmoon.dogsapp.data.repository.mappers

interface Mapper<E, D> {
    fun mapTo(type: E): D
}