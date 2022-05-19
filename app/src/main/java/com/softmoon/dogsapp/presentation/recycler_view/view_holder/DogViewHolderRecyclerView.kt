package com.softmoon.dogsapp.presentation.recycler_view.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.softmoon.dogsapp.databinding.ItemDogBinding
import com.softmoon.dogsapp.domain.model.Dog

class DogViewHolderRecyclerView(
    private val itemDogBinding: ItemDogBinding
): RecyclerView.ViewHolder(itemDogBinding.root) {
    fun bind(dog: Dog) {
        itemDogBinding.dog = dog
    }
}