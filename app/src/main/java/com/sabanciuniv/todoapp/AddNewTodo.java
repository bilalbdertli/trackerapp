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

import com.sabanciuniv.todoapp.model.ToDo;

public class AddNewTodo extends AppCompatActivity {

    EditText txtNewDueDate;
    EditText txtNewTodo;
    EditText txtNewTitle;
    Button btnAddTodo;
    ProgressBar progress;
    TextView txtError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todo);
        txtNewDueDate = findViewById(R.id.txtNewDuedate);
        txtNewTodo = findViewById(R.id.txtNewDescription);
        txtNewTitle = findViewById(R.id.txtNewTitle);
        txtError = findViewById(R.id.txtError);
        progress = findViewById(R.id.progressBar);
        btnAddTodo = findViewById(R.id.btnAddTodo);
        getSupportActionBar().setTitle("Add a New To-Do");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnAddTodo.setOnClickListener(v->{
            if(!(txtNewTodo.getText().toString().equals("") || txtNewDueDate.getText().toString().equals("") || txtNewTitle.getText().toString().equals("") )){
                progress.setVisibility(View.VISIBLE);
                txtNewDueDate.setEnabled(false);
                txtNewTodo.setEnabled(false);
                txtNewTitle.setEnabled(false);
                btnAddTodo.setEnabled(false);

                ToDo newToDo  =  new ToDo(txtNewTodo.getText().toString(), txtNewTitle.getText().toString(), txtNewDueDate.getText().toString());
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("newTodo", newToDo);
                startActivity(i);
            }
            else{
                txtError.setVisibility(View.VISIBLE);

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