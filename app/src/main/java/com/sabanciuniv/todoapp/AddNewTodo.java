package com.sabanciuniv.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sabanciuniv.todoapp.databinding.ActivityAddNewTodoBinding;
import com.sabanciuniv.todoapp.model.ToDo;

public class AddNewTodo extends AppCompatActivity {

    private ActivityAddNewTodoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Add a New To-Do");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.btnAddTodo.setOnClickListener(v->{
            if(!(binding.txtNewDescription.getText().toString().equals("") || binding.txtNewDuedate.getText().toString().equals("") || binding.txtNewTitle.getText().toString().equals("") )){
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.txtNewDuedate.setEnabled(false);
                binding.txtNewDescription.setEnabled(false);
                binding.txtNewTitle.setEnabled(false);
                binding.btnAddTodo.setEnabled(false);

                ToDo newToDo  =  new ToDo(binding.txtNewDescription.getText().toString(), binding.txtNewTitle.getText().toString(), binding.txtNewDuedate.getText().toString());
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("newTodo", newToDo);
                startActivity(i);
            }
            else{
                binding.txtError.setVisibility(View.VISIBLE);

            }

        });

        /*
        txtNewTodo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                txtNewTodo.setVisibility(View.INVISIBLE);
            }
        });
        txtNewDuedate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                txtNewDuedate.setVisibility(View.INVISIBLE);
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

}