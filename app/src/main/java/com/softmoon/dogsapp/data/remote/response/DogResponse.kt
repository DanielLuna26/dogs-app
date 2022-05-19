package com.softmoon.dogsapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class DogResponse(
    @SerializedName("dogName")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("image")
    val imgUrl: String
)
