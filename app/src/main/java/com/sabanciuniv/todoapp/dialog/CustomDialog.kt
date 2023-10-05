package com.sabanciuniv.todoapp.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabanciuniv.todoapp.`interface`.ResetDailyList
import com.sabanciuniv.todoapp.adapter.FoodListRecViewAdapter
import com.sabanciuniv.todoapp.databinding.CustomDialogBinding
import com.sabanciuniv.todoapp.model.Food
import com.sabanciuniv.todoapp.model.UserCalory
import com.sabanciuniv.todoapp.viewmodel.NoteViewModel
import com.sabanciuniv.todoapp.viewmodel.UseCaloryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll

class CustomDialog(context: Context, var dailyEaten: MutableList<Food>, var goal: Int, val earned: Int,
                   val resetDailyList: ResetDailyList, val parentLifecycleOwner: LifecycleOwner): Dialog(context) {
    private var binding: CustomDialogBinding? = null
    private val viewModel: UseCaloryViewModel = UseCaloryViewModel(UserCalory(earned, goal))
    private val dialogScope = CoroutineScope(Dispatchers.Main)
    private lateinit var dialogAdapter: FoodListRecViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomDialogBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        dialogAdapter = FoodListRecViewAdapter(dailyEaten,context)
        binding!!.viewmodel = viewModel
        binding!!.lifecycleOwner = parentLifecycleOwner
        binding!!.recView.adapter = dialogAdapter
        binding!!.recView.layoutManager = LinearLayoutManager(context)
        window!!.setLayout(
            ViewGroup.LayoutParams.FILL_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        setTitle(goal.toString())

    }

    override fun onStart() {
        super.onStart()
        binding!!.dismissButton.setOnClickListener {
            dismiss()
        }
        binding!!.resetButton.setOnClickListener {
            dialogScope.launch(){
                resetDailyList.onResetClicked()
                viewModel.resetClicked()
            }
            val size = dailyEaten.size
            dialogAdapter.notifyItemRangeRemoved(0, size)

        }
        binding!!.addButton.setOnClickListener {
            binding!!.additionalHolder.visibility = View.VISIBLE
            binding!!.addButton.isEnabled = false
        }

        binding!!.submitButton.setOnClickListener {
            try {
                val foodName = binding!!.foodText.text.toString()
                val foodCal = binding!!.calText.text.toString().toInt()
                if(foodName.isNotEmpty() && foodCal.toString().isNotEmpty()){
                    dialogScope.launch(){
                        resetDailyList.onAddClicked(foodName, foodCal)
                        viewModel.foodAdded(foodCal)
                    }
                    val newFood = Food(foodName, foodCal)
                    dailyEaten.add(newFood)
                    dialogAdapter.notifyItemInserted(dailyEaten.size - 1)
                    binding!!.recView.scrollToPosition(dailyEaten.size - 1)
                    binding!!.additionalHolder.visibility = View.GONE
                    binding!!.addButton.isEnabled = true
                }
                else{
                    TODO(" ENTRIES ARE EMPTY (OR ONE OF THEM)")
                }

            }
            catch (e:Exception){
                TODO("Implement what to do")
            }

        }
        binding!!.cancelButton.setOnClickListener {
            binding!!.additionalHolder.visibility = View.GONE
            binding!!.addButton.isEnabled = true
        }


    }


}