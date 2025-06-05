package com.alten.androidtut.sintaxis

fun main(){
 //inmutableList()
  listaMutable()
}

fun inmutableList(){
  val readOnly:List<String> = listOf ("lunes","martes","miercoles","jueves","viernes","sabado","domingo")
  println("tamanio ${readOnly.size}")
  println("data ${readOnly.toString()}")
  for (readStep in readOnly){
    println("--------- ${readStep}")
    println("-----primero---- ${readOnly.last()}")
    println("----ultimo----- ${readOnly.first()}")
    println("----los q contienen a----- ${readOnly.filter{
      it.contains("a")
    }}")
  }

  readOnly.forEach{diaDeLaSemana -> println(diaDeLaSemana)}



}


fun listaMutable(){
  val mutableList:MutableList<String> = mutableListOf("lunes","martes","miercoles","jueves","viernes","sabado","domingo")
  mutableList.add("diaextra")
  println("data $mutableList")
  mutableList.add(0,"diaextraelprinmero")
  println("data $mutableList")
  mutableList.removeAt(mutableList.size-1)
  println("data $mutableList")

}