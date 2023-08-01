package com.sabanciuniv.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.sabanciuniv.todoapp.databinding.ActivityTodoDetailsBinding;
import com.sabanciuniv.todoapp.model.ToDo;

public class TodoDetails extends AppCompatActivity {
    private ActivityTodoDetailsBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodoDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().setTitle("Details of To-Do");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ToDo current = getIntent().getSerializableExtra("todoDetails", ToDo.class);
        if(current != null){
            binding.seeDetailsTitle.setText(current.getTitle().toString());
            binding.seeDetailsDesc.setText(current.getToDo().toString());
            binding.seeDetailsDueDate.setText(current.getDueDate().toString());
        }
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }
}