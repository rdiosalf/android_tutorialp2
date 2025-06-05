package com.alten.androidtut.todoapp

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alten.androidtut.R

class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

  //obtención de cada nombre de categoria
  private val tvCateogryName: TextView = view.findViewById(R.id.tvCategoryName)

  //obtencion del divider
  private val divider: View = view.findViewById(R.id.divider)


  //cada item de categorias para luego ponerle color
  private val viewContainerCat: CardView = view.findViewById(R.id.viewContainerCat)

  //antes de pasarle la lambda que haría la renderizacion del rv normal sin funcionalidad de filtrado
  // fun render(taskCategory: TaskCategory) {
  fun render(taskCategory: TaskCategory, onItemSelected: (Int) -> Unit) {

    //con la funcion lambda enviada como segundo argumento puedo poner un listener
    //y solo cambio de color  si la cat esta seleccionada
    val color =
      if (taskCategory.isSelected) {
        R.color.todo_background_card
      } else {
        R.color.todo_background_disabled
      }
    //pongo color a cada card de categoria pero no color de background
    //sino backgroudn de toda la card view o sea la item_category NO - setbackgroundcolor SI - setcardbackgoundcolor
    viewContainerCat.setCardBackgroundColor(ContextCompat.getColor(viewContainerCat.context, color))

    itemView.setOnClickListener {
      onItemSelected(layoutPosition)
    }


    //renderizado de cada categoria
    when (taskCategory) {
      TaskCategory.Business -> {
        tvCateogryName.text =
          ContextCompat.getString(tvCateogryName.context, R.string.todo_category_business)
        //un color diferente para cada divider
        divider.setBackgroundColor(
          //acceso a los colores con el contextcompat
          //getColor necesita un contexto y el color
          ContextCompat.getColor(divider.context, R.color.todo_business_category)
        )
      }

      TaskCategory.Other -> {
        tvCateogryName.text =
          ContextCompat.getString(tvCateogryName.context, R.string.todo_category_others)
        divider.setBackgroundColor(
          ContextCompat.getColor(divider.context, R.color.todo_other_category)
        )
      }

      TaskCategory.Personal -> {
        tvCateogryName.text =
          ContextCompat.getString(tvCateogryName.context, R.string.todo_category_personal)
        divider.setBackgroundColor(
          ContextCompat.getColor(divider.context, R.color.todo_personal_category)
        )
      }
    }
  }
}