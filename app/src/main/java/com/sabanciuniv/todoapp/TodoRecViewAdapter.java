package com.sabanciuniv.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabanciuniv.todoapp.model.ToDo;

import java.util.List;

public class TodoRecViewAdapter extends RecyclerView.Adapter<TodoRecViewAdapter.TodoViewHolder> {
    List<ToDo> data;
    Context context;

    public TodoRecViewAdapter(List<ToDo> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.todo_row, parent, false);
        TodoViewHolder holder = new TodoViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.titleTodo.setText(data.get(position).getToDo());
        holder.dueDate.setText(data.get(position).getDueDate());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder{

        TextView titleTodo;
        TextView dueDate;

       public TodoViewHolder(@NonNull View itemView) {
           super(itemView);
           titleTodo = itemView.findViewById(R.id.txtTitleTodo);
           dueDate = itemView.findViewById(R.id.txtDueDate);
       }
   }
}
