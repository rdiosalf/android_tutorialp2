package com.alten.androidtut.superheroapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alten.androidtut.R


class SuperHeroAdapter(var superheroList: List<SuperHeroItemResponse> = emptyList(),private val onItemSelected:(String) ->Unit  ):
  RecyclerView.Adapter<SuperHeroViewHolder>() {


  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): SuperHeroViewHolder {
    //inflador de layouts de android
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_superhero, parent, false)
    return SuperHeroViewHolder(view)
  }

  override fun onBindViewHolder(
    holder: SuperHeroViewHolder,
    position: Int
  ) {
    val item = superheroList[position]
    holder.bind(item, onItemSelected)
  }


  /**
   * Devuelve el numero de elementos de la lista
   */
  override fun getItemCount(): Int {
    return superheroList.size
  }

  fun updateList(superheroList: List<SuperHeroItemResponse>){
    this.superheroList=superheroList
    notifyDataSetChanged()
  }

}