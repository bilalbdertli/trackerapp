package com.sabanciuniv.todoapp.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sabanciuniv.todoapp.adapter.FoodListRecViewAdapter
import com.sabanciuniv.todoapp.databinding.CustomDialogBinding
import com.sabanciuniv.todoapp.`interface`.ResetDailyList
import com.sabanciuniv.todoapp.model.Food
import com.sabanciuniv.todoapp.model.UserCalory
import com.sabanciuniv.todoapp.viewmodel.UseCaloryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
            dailyEaten.clear()
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
                    binding!!.foodText.text = null
                    binding!!.calText.text = null
                }
                else{
                    Snackbar.make(it, "Please fill all the fields.", Snackbar.LENGTH_LONG).show()

                }

            }
            catch (e:Exception){
                Snackbar.make(it, "Please fill all the fields.", Snackbar.LENGTH_LONG).show()

            }

        }
        binding!!.cancelButton.setOnClickListener {
            binding!!.additionalHolder.visibility = View.GONE
            binding!!.addButton.isEnabled = true
            binding!!.foodText.text = null
            binding!!.calText.text = null
        }


    }

    override fun dismiss() {
        //TODO: find a way to hide soft keyboard before dialog dismiss
        super.dismiss()

    }

    private fun hideSoftKeyboard(view: View?) {
        val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(this.currentFocus != null){
            imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
        }
    }
    fun closeInput(caller: View) {
        caller.postDelayed({
            val imm =
                caller.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(caller.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }, 100)
    }


}