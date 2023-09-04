package com.example.searchsuperheroesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.zip.Inflater

class SuperHeroAdapter(var SuperHeroList:List<SuperHeroItem> = emptyList()) : RecyclerView.Adapter<SuperheroViewHolder>(){

    fun updateList(superHeroList:List<SuperHeroItem>){
        this.SuperHeroList = superHeroList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder { //Especificamos el layout que vamos a utilizar
        return SuperheroViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent,false))
    }

    override fun getItemCount() = SuperHeroList.size

    override fun onBindViewHolder(viewHolder: SuperheroViewHolder, position: Int) {
        viewHolder.bind(SuperHeroList[position])
    }

}