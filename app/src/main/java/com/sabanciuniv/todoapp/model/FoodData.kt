package com.sabanciuniv.todoapp.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class FoodData(
    val food: String = "",
    val calories: Int = 2000
)

@Serializable
data class Food(
    val name: String,
    val calories: Int
)
