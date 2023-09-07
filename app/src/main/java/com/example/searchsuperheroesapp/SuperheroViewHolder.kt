package com.example.searchsuperheroesapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.searchsuperheroesapp.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperheroViewHolder(view : View):RecyclerView.ViewHolder(view){

    private val binding = ItemSuperheroBinding.bind(view)

    fun bind(superHeroItem: SuperHeroItem,onItemSelected: (String) -> Unit){
        binding.tvSuperHeroName.text = superHeroItem.superHeroName
        Picasso.get().load(superHeroItem.superHeroImage.url).into(binding.ivSuperHero)
        binding.root.setOnClickListener { onItemSelected(superHeroItem.superHeroId) }
    }
}