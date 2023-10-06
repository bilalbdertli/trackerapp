package com.sabanciuniv.todoapp.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.sabanciuniv.todoapp.databinding.GoalpickerDialogBinding
import com.sabanciuniv.todoapp.`interface`.ResetDailyList

class GoalPickerDialog(context: Context,
): Dialog(context) {
    private var binding: GoalpickerDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GoalpickerDialogBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val minValue = 1000
        val maxValue = 4000
        val stepSize = 100
        binding!!.numberPicker.minValue = 0
        binding!!.numberPicker.maxValue = (maxValue - minValue) / stepSize
        val displayedValues = Array(binding!!.numberPicker.maxValue + 1) {
            (minValue + it * stepSize).toString()
        }
        binding!!.numberPicker.displayedValues = displayedValues
        binding!!.numberPicker.value = (2000 - minValue) / stepSize // Set val

    }
}