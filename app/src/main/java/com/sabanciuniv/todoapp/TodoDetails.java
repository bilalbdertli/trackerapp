package com.sabanciuniv.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.sabanciuniv.todoapp.databinding.ActivityTodoDetailsBinding;
import com.sabanciuniv.todoapp.model.ToDo;
import com.sabanciuniv.todoapp.model.ToDoRepository;

import java.util.concurrent.ExecutorService;

public class TodoDetails extends AppCompatActivity {
    private ActivityTodoDetailsBinding binding;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Log.i("DELETE", msg.obj.toString());
            finish();


            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodoDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ToDo current = (ToDo) getIntent().getSerializableExtra("todoDetails");


        getSupportActionBar().setTitle("Details of To-Do");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(current != null){
            binding.seeDetailsTitle.setText(current.getTitle());
            binding.seeDetailsDesc.setText(current.getToDo());
            binding.seeDetailsDueDate.setText(current.getDueDate());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }
        else if(item.getItemId()==R.id.mnDeleteToDo){
        ExecutorService srv = ((ToDoApplication)getApplication()).srv;
        ToDo current = (ToDo) getIntent().getSerializableExtra("todoDetails");

        ToDoRepository repo = new ToDoRepository();
        repo.deleteToDoById(srv, current.getId(), handler);
        finish();
        }
        return true;
    }
}