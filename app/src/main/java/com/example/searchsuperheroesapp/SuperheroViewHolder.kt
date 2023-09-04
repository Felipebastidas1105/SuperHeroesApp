package com.example.searchsuperheroesapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.searchsuperheroesapp.databinding.ItemSuperheroBinding

class SuperheroViewHolder(view : View):RecyclerView.ViewHolder(view){

    private val binding = ItemSuperheroBinding.bind(view)

    fun bind(superHeroItem: SuperHeroItem){
        binding.tvSuperHeroName.text = superHeroItem.superHeroName
    }
}