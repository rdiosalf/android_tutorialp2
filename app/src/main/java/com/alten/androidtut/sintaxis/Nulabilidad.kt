package com.alten.androidtut.sintaxis

fun main(){
  var variablNulable:String ?=null
  variablNulable= verdad(true)
  println(variablNulable?.get(1) ?: "ya no es nulo")
  variablNulable= verdad(false)
  println(variablNulable?.get(1) ?: "ojo q es nulo ")



}

fun verdad(bool:Boolean): String? {
  if (bool)
  {
   return   "AAAAAA"
  }else{
    return null
  }
}