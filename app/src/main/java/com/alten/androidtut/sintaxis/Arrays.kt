package com.alten.androidtut.sintaxis

fun main(){
  var daysOfWeek = arrayOf("lunes","martes","miercoles","jueves","viernes","sabado","domingo")
 var i=0
  for ( i in 0  until 7)
  println("posicion $i para array de size ${daysOfWeek.size}  ${daysOfWeek[i]}")
  i++

  for (dayOfWeek in daysOfWeek){
    println("Today is $dayOfWeek")
  }
}