package com.sabanciuniv.todoapp.`interface`

interface ResetDailyList {
    suspend fun onResetClicked()
    suspend fun onAddClicked(name: String, cal: Int)


}