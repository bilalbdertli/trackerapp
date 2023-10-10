package com.sabanciuniv.todoapp.model

import kotlinx.serialization.Serializable

@Serializable
data class ChartData(
    val index: Int,
    val caloryGoal: Int,
    val earnedCalories: Int
)