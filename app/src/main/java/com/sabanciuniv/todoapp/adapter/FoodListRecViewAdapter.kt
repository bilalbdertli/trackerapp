package com.sabanciuniv.todoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sabanciuniv.todoapp.databinding.NoteRowBinding
import com.sabanciuniv.todoapp.databinding.TodoRowBinding
import com.sabanciuniv.todoapp.model.Food
import com.sabanciuniv.todoapp.model.ToDo

class FoodListRecViewAdapter(
    var data: List<Food>,
    var context: Context):
    RecyclerView.Adapter<FoodListRecViewAdapter.FoodListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        return FoodListViewHolder(
            NoteRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: FoodListViewHolder, position: Int) {
        holder.binding.NoteDueDate.text = data[position].calories.toString()
        holder.binding.NoteTitleTodo.text = data[position].name
    }
    override fun getItemCount(): Int {
        return data.size
    }

    inner class FoodListViewHolder( val binding: NoteRowBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}