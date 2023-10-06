package com.sabanciuniv.todoapp.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.sabanciuniv.todoapp.databinding.GoalpickerDialogBinding
import com.sabanciuniv.todoapp.`interface`.ResetDailyList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoalPickerDialog(context: Context, var currentGoal: Int,val resetDailyList: ResetDailyList
): Dialog(context) {
    private var binding: GoalpickerDialogBinding? = null
    private var selectedValue = 2000
    private val dialogScope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GoalpickerDialogBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        window!!.setLayout(
            ViewGroup.LayoutParams.FILL_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        binding!!.caloryText.text = currentGoal.toString()
        val minValue = 1000
        val maxValue = 4000
        val stepSize = 100
        binding!!.numberPicker.minValue = 0
        binding!!.numberPicker.maxValue = (maxValue - minValue) / stepSize
        val displayedValues = Array(binding!!.numberPicker.maxValue + 1) {
            (minValue + it * stepSize).toString()
        }
        binding!!.numberPicker.displayedValues = displayedValues
        binding!!.numberPicker.value =(currentGoal / stepSize) - 10

        binding!!.numberPicker.setOnValueChangedListener { _, _, newVal ->
            selectedValue = minValue + newVal * stepSize
            binding!!.caloryText.text = selectedValue.toString()
        }
    }

    override fun onStart() {
        super.onStart()

        binding!!.dismissButton.setOnClickListener {
            dismiss()
        }
        binding!!.submitButton.setOnClickListener {
            dialogScope.launch {
                resetDailyList.onGoalChanged(selectedValue)
            }
            dismiss()
        }

    }
}