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

class CaloryFragment(val dataStore: DataStore<FoodData> ) : Fragment() {
   private var binding: FragmentCaloryBinding? = null
    private var calories: Int = 2000

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
        binding!!.btn.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO) {
                addList()
            }


        }

        binding!!.btn3.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                getAllFoods()
            }
        }

        lifecycleScope.launch {
            calories = getCalories()
            binding!!.editTextNumber.setText(calories.toString())
        }
    }

    private suspend fun getAllFoods(){
        dataStore.data.collect(){
            val foodListAsString = it.food.joinToString(separator = "\n") { food ->
                "Name: ${food.name}, Calories: ${food.calories}"
            }
            Log.i("DEVELOPMENT", /*foodListAsString*/it.calories.toString())
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

    private suspend fun addList(){
        dataStore.updateData { it ->
            it.copy(
                food = it.food.mutate {
                    it.add(Food("aaa", 300))
                }
            )
        }
    }
}