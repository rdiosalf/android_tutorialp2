package com.alten.androidtut.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alten.androidtut.R

//antes de hacer lo de las func lambda en el adapter class TaskAdapter (private val tasks:List<Task>):
//uso de lambda paso una función como parámetro que devuelve que recibe una posición y no devuelve nada
class TasksAdapter( var tasks: List<Task>, private val onTaskSelected: (Int) -> Unit) :
  RecyclerView.Adapter<TasksViewHolder>() {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): TasksViewHolder {
    //inflador de layouts de android
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_todo_task, parent, false)
    return TasksViewHolder(view)
  }

  override fun onBindViewHolder(
    holder: TasksViewHolder,
    position: Int
  ) {
    holder.render(tasks[position])//renderizado según la posición, donde la func render la he declarado en el holder
    //solo cuando se clica uso la lambda
    holder.itemView.setOnClickListener {
      onTaskSelected(position)//enlazado de la función lambda
    }

  }


  override fun getItemCount() = tasks.size

}