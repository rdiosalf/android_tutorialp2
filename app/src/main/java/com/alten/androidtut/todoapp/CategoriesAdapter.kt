package com.alten.androidtut.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alten.androidtut.R

//antes de meter la func lambda class CategoriesAdapter(private val categories:List<TaskCategory>):
class CategoriesAdapter(private val categories:List<TaskCategory>,private val onItemSelected:(Int) -> Unit):
  RecyclerView.Adapter<CategoriesViewHolder>() {


  /**
   * crea una vista que tengo hago en layouts nueva
    */
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): CategoriesViewHolder {
    //inflador de layouts de android
    val view= LayoutInflater.from(parent.context)
      .inflate(R.layout.item_task_category,parent,false)
    return CategoriesViewHolder(view)
  }

  override fun onBindViewHolder(
    holder: CategoriesViewHolder,
    position: Int
  ) {
    //holder.render(categories[position])//renderizado según la posición, donde la func render la he declarado en el holder
    holder.render(categories[position], onItemSelected)
  }

  /**
   * Devuelve el numero de elementos de la lista
   */
  override fun getItemCount(): Int {
   return categories.size
  }
}

