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

import com.sabanciuniv.todoapp.databinding.ActivityMainBinding;
import com.sabanciuniv.todoapp.model.ToDo;
import com.sabanciuniv.todoapp.model.ToDoRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    List<ToDo> todoList = new ArrayList<>();


    ToDoRepository repo = new ToDoRepository();
    Handler dataHandler =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            todoList = (List<ToDo>)msg.obj;
            Toast.makeText(MainActivity.this.getApplicationContext(), "none", Toast.LENGTH_SHORT).show();

            if(todoList.size() == 0){
                TodoRecViewAdapter todoRecViewAdapter = new TodoRecViewAdapter(todoList, MainActivity.this);
                binding.recTodo.setAdapter(todoRecViewAdapter);
                binding.recTodo.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                binding.recTodo.setVisibility(View.INVISIBLE);
                binding.zeroTodo.setVisibility(View.VISIBLE);

            }
            else{
                Toast.makeText(MainActivity.this.getApplicationContext(), "thereexists", Toast.LENGTH_SHORT).show();

                binding.recTodo.setVisibility(View.VISIBLE);
                binding.zeroTodo.setVisibility(View.INVISIBLE);
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().setTitle("To-Do's of the Day");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //extra
        /*
        ToDo todo1 = new ToDo("the description of the todo for the title named as finish the mobile app", "finish the mobile app", "tomorrow" );
        ToDo todo2 = new ToDo("the description of the todo for the title named as finish the js course", "finish the js course", "01/08/2023" );
        ToDo todo3 = new ToDo("the description of the todo for the title named as finish the work on website ", "finish the work on website", "01/08/2023" );
        todoList.add(todo1);
        todoList.add(todo2);
        todoList.add(todo3);
        */







    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ToDo newToDo = intent.getSerializableExtra("newTodo", ToDo.class);
        todoList.add(newToDo);
        binding.recTodo.getAdapter().notifyItemInserted(todoList.size()-1);
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
        repo.getAllToDos(((ToDoApplication)getApplication()).srv,dataHandler);

        /*
        if(todoList.size() == 0){


            binding.recTodo.setVisibility(View.INVISIBLE);
            binding.zeroTodo.setVisibility(View.VISIBLE);
        }
        else{


            binding.recTodo.setVisibility(View.VISIBLE);
            binding.zeroTodo.setVisibility(View.INVISIBLE);
        }
        */



    }
}