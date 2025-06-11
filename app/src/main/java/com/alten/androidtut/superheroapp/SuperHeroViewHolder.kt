package com.alten.androidtut.superheroapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alten.androidtut.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperHeroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

  private val binding = ItemSuperheroBinding.bind(view)
  fun bind(superHeroItemResponse: SuperHeroItemResponse, onItemSelected:(String) ->Unit){
    binding.tvSuperheroName.text=superHeroItemResponse.name
    //uso según documentacion lib Picasso - traeme picaso y carga esta url en el imageview
    Picasso.get().load(superHeroItemResponse.superheroImage.url).into(binding.ivSuperhero)
    //cada vez q alguien puse en la tarjetita se ejecuta la funcion lambda
    binding.root.setOnClickListener { onItemSelected(superHeroItemResponse.superheroId) }


  }

}