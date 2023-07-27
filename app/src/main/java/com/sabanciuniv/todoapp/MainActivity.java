package com.sabanciuniv.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sabanciuniv.todoapp.model.ToDo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recView;
    List<ToDo> todoList =new ArrayList<>();
    TextView zeroTodos;

    //Adding the trials


    //end of trial
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recView =findViewById(R.id.recTodo);
        zeroTodos = findViewById(R.id.zeroTodo);
        getSupportActionBar().setTitle("To-Do's of the Day");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //extra

        ToDo todo1 = new ToDo("the description of the todo for the title named as finish the mobile app", "finish the mobile app", "tomorrow" );
        ToDo todo2 = new ToDo("the description of the todo for the title named as finish the js course", "finish the js course", "01/08/2023" );
        ToDo todo3 = new ToDo("the description of the todo for the title named as finish the work on website ", "finish the work on website", "01/08/2023" );
        todoList.add(todo1);
        todoList.add(todo2);
        todoList.add(todo3);

        ToDo newToDo = getIntent().getSerializableExtra("newTodo", ToDo.class);
        if(newToDo != null){todoList.add(newToDo);
        }

        TodoRecViewAdapter todoRecViewAdapter = new TodoRecViewAdapter(todoList, MainActivity.this);
        recView.setAdapter(todoRecViewAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));




    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }else if(item.getItemId()==R.id.mnPostComment){
            //to post activity
            Intent i = new Intent(this, AddNewTodo.class);
            startActivity(i);

        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();



        if(todoList.size() == 0){


            recView.setVisibility(View.INVISIBLE);
            zeroTodos.setVisibility(View.VISIBLE);
        }
        else{


            recView.setVisibility(View.VISIBLE);
            zeroTodos.setVisibility(View.INVISIBLE);
        }




    }
}