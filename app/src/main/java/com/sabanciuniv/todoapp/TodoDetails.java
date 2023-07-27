package com.sabanciuniv.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.sabanciuniv.todoapp.model.ToDo;

public class TodoDetails extends AppCompatActivity {
    TextView seeDetailsTitle;
    TextView seeDetailsDescription;
    TextView seeDetailsText;
    TextView seeDetailsDueDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);

        seeDetailsTitle = findViewById(R.id.seeDetailsTitle);
        seeDetailsDescription = findViewById(R.id.seeDetailsDesc);
        seeDetailsText = findViewById(R.id.seeDetailsText);
        seeDetailsDueDate = findViewById(R.id.seeDetailsDueDate);
        getSupportActionBar().setTitle("Details of To-Do");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ToDo current = getIntent().getSerializableExtra("todoDetails", ToDo.class);
        if(current != null){
            seeDetailsTitle.setText(current.getTitle().toString());
            seeDetailsDescription.setText(current.getToDo().toString());
            seeDetailsDueDate.setText(current.getDueDate().toString());
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