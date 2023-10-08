package com.sabanciuniv.todoapp.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class FoodData(
    val calories: Int = 2000,
    val consumed: Int = 0,
    val currentDay: String = "",
    val foodList: MutableList<Food> = mutableListOf()
)

@Serializable
data class Food(
    val name: String,
    val calories: Int
)

@Serializable
data class RecentDayData(
    val day: String,
    val caloryGoal: Int,
    val earnedCalories: Int
)
