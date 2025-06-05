package com.alten.androidtut.todoapp

//sealed class TaskCategory {
//con un argumento para todos los objetos q me indicarán si está o no selecconado
sealed class TaskCategory(var isSelected: Boolean = true) {//true porque las quiero todas azules no deshabilitadas, luego cuando seleccione deshabilitaré resto
  object Business : TaskCategory()
  object Personal : TaskCategory()
  object Other : TaskCategory()
  //object NuevaCategoria : TaskCategory() para probar el sentido de la clase sealed

}

