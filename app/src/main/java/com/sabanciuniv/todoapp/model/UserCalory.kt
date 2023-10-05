package com.sabanciuniv.todoapp.model

data class UserCalory(val earned: Int, val goal: Int){
    val percentage: Int = ((earned.toDouble() / goal) * 100).toInt().coerceAtMost(100)
    val title: String = "$earned/$goal"
    val percentageText: String = "$percentage%"
}
