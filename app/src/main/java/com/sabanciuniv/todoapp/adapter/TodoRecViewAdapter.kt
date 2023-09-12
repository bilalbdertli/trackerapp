package com.sabanciuniv.todoapp

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sabanciuniv.todoapp.TodoRecViewAdapter.TodoViewHolder
import com.sabanciuniv.todoapp.activity.TodoDetails
import com.sabanciuniv.todoapp.databinding.TodoRowBinding
import com.sabanciuniv.todoapp.`interface`.RecyclerViewInterface
import com.sabanciuniv.todoapp.model.ToDo
import com.sabanciuniv.todoapp.repository.ToDoRepository

class TodoRecViewAdapter(
    var data: List<ToDo>,
    var context: Context,
    var checkListener: RecyclerViewInterface
) :
    RecyclerView.Adapter<TodoViewHolder>() {
    var repo = ToDoRepository()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TodoRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.txtTitleTodo.text = data[position].title
        holder.binding.txtDueDate.text = data[position].dueDate
        holder.binding.checkBox.isChecked = data[position].isChecked
        val currentFlags = holder.binding.txtTitleTodo.paintFlags
        if (holder.binding.checkBox.isChecked) {
            holder.binding.txtTitleTodo.paintFlags = currentFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.binding.txtTitleTodo.paintFlags =
                currentFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        holder.binding.detailsContainer.setOnClickListener {
            val i = Intent(context, TodoDetails::class.java)
            i.putExtra("todoDetails", data[holder.adapterPosition])
            context.startActivity(i)
        }
        holder.binding.checkBox.setOnClickListener { v ->
            if (!data[holder.adapterPosition].isLoading) {
                data[holder.adapterPosition].isLoading = true
                if (!holder.binding.checkBox.isChecked) {
                    holder.binding.txtTitleTodo.paintFlags =
                        currentFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                } else {
                    holder.binding.txtTitleTodo.paintFlags =
                        currentFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                data[holder.adapterPosition].isLoading = false

                data[holder.adapterPosition].id?.let {
                    checkListener.onCheckboxClicked(holder.adapterPosition,
                        it
                    )
                }
            } else {
                Log.i("BUSY", "CANNOT DO RIGHT NOW")
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class TodoViewHolder( val binding: TodoRowBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}