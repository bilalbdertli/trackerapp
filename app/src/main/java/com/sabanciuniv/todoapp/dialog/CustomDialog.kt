package com.sabanciuniv.todoapp.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabanciuniv.todoapp.`interface`.ResetDailyList
import com.sabanciuniv.todoapp.adapter.FoodListRecViewAdapter
import com.sabanciuniv.todoapp.databinding.CustomDialogBinding
import com.sabanciuniv.todoapp.model.Food
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll

class CustomDialog(context: Context, var dailyEaten: List<Food>, var goal: Int, val resetDailyList: ResetDailyList): Dialog(context) {
    private var binding: CustomDialogBinding? = null
    private val dialogScope = CoroutineScope(Dispatchers.Main)
    private lateinit var dialogAdapter: FoodListRecViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomDialogBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        dialogAdapter = FoodListRecViewAdapter(dailyEaten,context)
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
            }
            dialogAdapter.notifyItemRangeRemoved(0, dailyEaten.size -1 )

        }
        binding!!.addButton.setOnClickListener {
            binding!!.additionalHolder.visibility = View.VISIBLE
        }

        binding!!.submitButton.setOnClickListener {
            Log.i("DEV", "CLICKABLE")
        }
        binding!!.cancelButton.setOnClickListener {
            binding!!.additionalHolder.visibility = View.GONE
        }


    }


}