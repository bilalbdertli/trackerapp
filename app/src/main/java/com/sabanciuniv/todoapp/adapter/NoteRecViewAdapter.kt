package com.sabanciuniv.todoapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sabanciuniv.todoapp.activity.NoteDetails
import com.sabanciuniv.todoapp.databinding.NoteRowBinding
import com.sabanciuniv.todoapp.model.Note
import com.sabanciuniv.todoapp.repository.ToDoRepository

class NoteRecViewAdapter(
    var data: List<Note>,
    var context: Context
):
    RecyclerView.Adapter<NoteRecViewAdapter.NoteViewHolder>()
{
    var repo = ToDoRepository()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.NoteTitleTodo.text = data[position].title
        holder.binding.NoteDueDate.text = data[position].dueDate
        holder.binding.NotedetailsContainer.setOnClickListener {
            val i = Intent(context, NoteDetails::class.java)
            i.putExtra("noteDetails", data[holder.adapterPosition])
            context.startActivity(i)
        }
    }

    inner class NoteViewHolder(val binding: NoteRowBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}