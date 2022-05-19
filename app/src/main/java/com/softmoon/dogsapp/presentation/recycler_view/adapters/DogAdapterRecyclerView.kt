package com.softmoon.dogsapp.presentation.recycler_view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softmoon.dogsapp.databinding.ItemDogBinding
import com.softmoon.dogsapp.domain.model.Dog
import com.softmoon.dogsapp.presentation.recycler_view.view_holder.DogViewHolderRecyclerView
import com.softmoon.dogsapp.utils.delegates.basicDiffUtil

class DogAdapterRecyclerView: RecyclerView.Adapter<DogViewHolderRecyclerView>() {

    var items: List<Dog> by basicDiffUtil(
        areContentsTheSame = { old, new ->
            old == new
        },
        areItemsTheSame = { old, new ->
            old.id == new.id
        }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolderRecyclerView {
        return DogViewHolderRecyclerView(
                ItemDogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DogViewHolderRecyclerView, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}