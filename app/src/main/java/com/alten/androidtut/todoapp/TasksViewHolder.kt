package com.alten.androidtut.todoapp

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alten.androidtut.R

class TasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

  //obtención de cada nombre de tarea
  private val tvTask: TextView = view.findViewById(R.id.tvTask)

  //obtencion de cada checkbox
  private val cbTask: CheckBox = view.findViewById(R.id.cbTask)


  fun render(task: Task) {
    //obtenemos los datos de la data class Task
    /* Log.i(FirstAppActivity.TAG,"renderizando ....${task.name}")
     tvTask.text = task.name*/
    //tacha y destacha y pinta el nombre
    if (task.isSelected) {
      tvTask.paintFlags = tvTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
      tvTask.paintFlags = tvTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
    //
    cbTask.isChecked = task.isSelected
    //cambio de color por codigo no en el xml, pero esto pondría a todos de un color sin importar categoria
    /*cbTask.buttonTintList= ColorStateList.valueOf(
      ContextCompat.getColor(cbTask.context,R.color.todo_background_todo_app)
    )*/
    //cambio de color en funcion de la categoria

    //refactorizado abajo
    /*when(task.category){
      refactorizado TaskCategory.Business ->{
        cbTask.buttonTintList= ColorStateList.valueOf(
          ContextCompat.getColor(cbTask.context,R.color.todo_business_category)
        )
      }
      TaskCategory.Other -> {
        cbTask.buttonTintList= ColorStateList.valueOf(
          ContextCompat.getColor(cbTask.context,R.color.todo_personal_category)
        )
      }
      TaskCategory.Personal -> {
        cbTask.buttonTintList= ColorStateList.valueOf(
          ContextCompat.getColor(cbTask.context,R.color.todo_other_category)
        )
      }}*/

    val color = when (task.category) {
      TaskCategory.Business -> R.color.todo_business_category
      TaskCategory.Other -> R.color.todo_personal_category
      TaskCategory.Personal -> R.color.todo_other_category
    }

    cbTask.buttonTintList = ColorStateList.valueOf(
      ContextCompat.getColor(cbTask.context, color)
    )

    tvTask.text = task.name
  }
}