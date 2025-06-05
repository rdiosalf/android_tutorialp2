package com.alten.androidtut.sintaxis

fun main(){
  println("llamada a la funcion result con entero\n")
  result(1)
  println("llamada a la funcion result con String\n")
  result("cadena")
  println("llamada a la funcion result con boolean true\n")
  result(true)
  println("llamada a funcion dame semestre para mes 1\n")
  getSemester(1)
}

fun result(value:Any){
  when(value){
    is Int -> print ("el valor resultado de sumarlo dos veces es ${value + value}")
    is String -> print("el valor es $value")
    is Boolean -> if(value) print ("el valor es true")
  }
}

fun getSemester(month:Int):String{
 val result= when(month){
    in 1..6-> "Primer semestre"
    in 7 ..12 ->"Segundo semestre"
    !in 1..12 -> "Erróneo"
    else -> "desconocido"
  }
  return result
}