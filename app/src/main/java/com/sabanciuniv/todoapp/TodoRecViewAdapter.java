package com.sabanciuniv.todoapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabanciuniv.todoapp.databinding.TodoRowBinding;
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
        /*
        View root = LayoutInflater.from(context).inflate(R.layout.todo_row, parent, false);
        TodoViewHolder holder = new TodoViewHolder(root);
        return holder;*/
        return new TodoViewHolder(TodoRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));


    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.binding.txtTitleTodo.setText(data.get(position).getTitle());
        holder.binding.txtDueDate.setText(data.get(position).getDueDate());
        holder.binding.detailsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TodoDetails.class);
                i.putExtra("todoDetails", data.get(holder.getAdapterPosition()));
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder{
        private TodoRowBinding binding;

        public  TodoViewHolder(TodoRowBinding binding){
            super(binding.getRoot());
            this.binding = binding;

            binding.checkBox.setOnClickListener(v->{
                Toast.makeText(context.getApplicationContext(), binding.txtTitleTodo.getText().toString(), Toast.LENGTH_SHORT).show();


                int currentFlags = binding.txtTitleTodo.getPaintFlags();
                if ((currentFlags & Paint.STRIKE_THRU_TEXT_FLAG) == Paint.STRIKE_THRU_TEXT_FLAG) {
                    // The STRIKE_THRU_TEXT_FLAG is currently set, remove it
                    binding.txtTitleTodo.setPaintFlags(currentFlags & ~Paint.STRIKE_THRU_TEXT_FLAG);

                } else {
                    // The STRIKE_THRU_TEXT_FLAG is not set, add it
                    binding.txtTitleTodo.setPaintFlags(currentFlags | Paint.STRIKE_THRU_TEXT_FLAG);

                }

            });
        }
   }
}
