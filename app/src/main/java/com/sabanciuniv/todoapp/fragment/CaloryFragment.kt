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

class CaloryFragment(private val dataStore: DataStore<FoodData> ) : Fragment() {
   private var binding: FragmentCaloryBinding? = null
    private var calories: Int = 2000
    private var foodItems: MutableList<Food> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        lifecycleScope.launch {
            calories = getCalories()
        }

        lifecycleScope.launch {
               foodItems = getFoodItems()
        }
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
        val foodData = dataStore.data.first()
        return foodData.calories
    }

    private suspend fun getFoodItems(): MutableList<Food>{
        val foodData = dataStore.data.first()
        return parseFoodListFromString(foodData.food)
    }

    private suspend fun addList(name:String, cal:Int){
        dataStore.updateData { it ->
            val newItem : String = "$name,$cal"
            val updatedFoodListAsString =
                if (it.food.isEmpty()) {
                newItem
            }
                else {
                "${it.food}:$newItem"
            }
            it.copy(food = updatedFoodListAsString)
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
    fun calculateTotalCalories(foodList: List<Food>): Int {
        return foodList.sumOf { it.calories }
    }



}