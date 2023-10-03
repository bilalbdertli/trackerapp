package com.sabanciuniv.todoapp.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabanciuniv.todoapp.R
import com.sabanciuniv.todoapp.ToDoApplication
import com.sabanciuniv.todoapp.activity.dataStore
import com.sabanciuniv.todoapp.databinding.FragmentCaloryBinding
import com.sabanciuniv.todoapp.model.Food
import com.sabanciuniv.todoapp.model.FoodData
import com.sabanciuniv.todoapp.model.FoodDataSerializer
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

class CaloryFragment(private val dataStore: DataStore<FoodData> ) : Fragment() {
   private var binding: FragmentCaloryBinding? = null
    private var calories: Int = 2000
    private var earnedCals: Int = 0
    private var foodItems: MutableList<Food> = mutableListOf()
    private var displayCalories: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            calories = getCalories()
        }

        lifecycleScope.launch {
            foodItems = getFoodItems()
            earnedCals = calculateTotalCalories(foodItems)
            displayCalories = "$earnedCals/$calories"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCaloryBinding.inflate(inflater, container, false)
        val v: View = binding!!.root
        binding!!.recView.layoutManager = LinearLayoutManager(context)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.textInputCalory.setText(displayCalories)

        binding!!.button.setOnClickListener {
            lifecycleScope.launch {
                getCalories()
            }
            binding!!.textInputCalory.setText(displayCalories)

        }

    }

    private suspend fun getDate(): String{
        return dataStore.data.first().currentDay
    }
    private suspend fun initialize(){
        Log.i("initial", getDate().toString())
        Log.i("initial", LocalDate.now().toString())
        if(LocalDate.now().toString() != getDate()){
            deleteDailyList()
        }
        calories = getCalories()
        foodItems = getFoodItems()
        earnedCals = calculateTotalCalories(foodItems)
        displayCalories = "$earnedCals/$calories"

    }
    private suspend fun addFoodItem(){
        addList("exampleFood", 500)
        initialize()
    }

    private suspend fun changeDailyGoal(t:Int){
        setCalories(t)
        calories = getCalories()
        displayCalories = "$earnedCals/$calories"

    }



    private suspend fun getAllFoods(){
        dataStore.data.collect(){
            Log.i("DEVELOPMENT", it.food)
        }

    }

    private suspend fun setCalories(t: Int){
        dataStore.updateData {
            it.copy(
                calories = t
            )
        }
    }

    private suspend fun getCalories(): Int{
        return dataStore.data.first().calories
    }

    private suspend fun getFoodItems(): MutableList<Food>{
        val foodData = dataStore.data.first()
        return parseFoodListFromString(foodData.food)
    }

    private suspend fun deleteDailyList(){
        dataStore.updateData {
            it.copy(
                food = ""
            )
        }
    }

    /*
    private suspend fun addList(name:String, cal:Int){
        dataStore.updateData { it ->
            val newItem : String = "$name,$cal"
            val dateNow = LocalDate.now().toString()
            val updatedFoodListAsString =
                if (it.food.isEmpty() || it.currentDay != dateNow) {
                    newItem
                }
                else {
                    "${it.food}:$newItem"
                }
            val updatedCurrentDay =
                if(it.currentDay.isEmpty() || it.currentDay !=dateNow) {
                    dateNow
                } else {
                    it.currentDay
                }

            it.copy(food = updatedFoodListAsString, currentDay = updatedCurrentDay)
        }
    }*/


    private suspend fun addList(name: String, cal: Int) {
        dataStore.updateData { it ->
            val newItem: String = "$name,$cal"
            val updatedCurrentDay = LocalDate.now().toString()

            val updatedFoodListAsString =
                if (it.currentDay != updatedCurrentDay) {
                    // If the currentDay doesn't match the current date, replace with the new item
                    newItem
                } else {
                    // If currentDay matches the current date, append the new item
                    "${it.food}:$newItem"
                }

            it.copy(food = updatedFoodListAsString, currentDay = updatedCurrentDay)
        }
    }

    private fun parseFoodListFromString(foodListAsString: String): MutableList<Food> {
        val items = foodListAsString.split(":")
        val foodList = mutableListOf<Food>()

        for (item in items) {
            val parts = item.split(",")
            if (parts.size == 2) {
                val name = parts[0]
                val calories = parts[1].toIntOrNull()

                if (calories != null) {
                    foodList.add(Food(name, calories))
                }
            }
        }

        return foodList
    }
    private fun calculateTotalCalories(foodList: MutableList<Food>): Int {
        return foodList.sumOf { it.calories }
    }
}