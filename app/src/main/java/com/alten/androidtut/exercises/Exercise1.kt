package com.alten.androidtut.exercises

fun main(){
  val morningNotification =51
  val eveningNotification =135

  printNotificationSummary(morningNotification)
  printNotificationSummary(morningNotification)
}

fun printNotificationSummary(numberOfMessages:Int ){
  if (numberOfMessages>99){
    println("")
    println("your haver more than 99 notifications")
  }else{
    println("you have $numberOfMessages notifications ")
  }
}