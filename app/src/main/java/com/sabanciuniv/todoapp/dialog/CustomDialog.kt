package com.sabanciuniv.todoapp.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabanciuniv.todoapp.adapter.FoodListRecViewAdapter
import com.sabanciuniv.todoapp.databinding.CustomDialogBinding
import com.sabanciuniv.todoapp.model.Food

class CustomDialog(context: Context, var dailyEaten: List<Food>, var goal: Int): Dialog(context) {
    private var binding: CustomDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomDialogBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val dialogAdapter = FoodListRecViewAdapter(dailyEaten,context)
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
    }


}