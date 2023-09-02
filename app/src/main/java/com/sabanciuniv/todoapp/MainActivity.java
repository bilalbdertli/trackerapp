package com.sabanciuniv.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.elevation.SurfaceColors;
import com.sabanciuniv.todoapp.databinding.ActivityMainBinding;
import com.sabanciuniv.todoapp.model.ToDo;
import com.sabanciuniv.todoapp.model.ToDoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity implements TodoRecViewAdapter.CheckListener {
    private ActivityMainBinding binding;

    private final TodoRecViewAdapter.CheckListener checkListener = new TodoRecViewAdapter.CheckListener() {
        @Override
        public void onButtonClicked(String id) {
            repo.changeChecked(((ToDoApplication)getApplication()).srv, id);
        }
    };
    List<ToDo> todoList = new ArrayList<>();

    ToDoRepository repo = new ToDoRepository();
    Handler dataHandler =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            todoList = (List<ToDo>)msg.obj;

            /*Toast.makeText(MainActivity.this.getApplicationContext(), "none", Toast.LENGTH_SHORT).show();*/
            binding.prgBar.setVisibility(View.INVISIBLE);
            if(todoList.size() == 0){
                binding.recTodo.setVisibility(View.INVISIBLE);
                binding.zeroTodo.setVisibility(View.VISIBLE);

            }
            else{
                TodoRecViewAdapter todoRecViewAdapter = new TodoRecViewAdapter(todoList, MainActivity.this, checkListener);
                binding.recTodo.setAdapter(todoRecViewAdapter);
                binding.recTodo.setLayoutManager(new LinearLayoutManager(MainActivity.this));
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getWindow().setStatusBarColor(SurfaceColors.SURFACE_2.getColor(this));
        getWindow().setNavigationBarColor(SurfaceColors.SURFACE_2.getColor(this));

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    /*
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ToDo newToDo = intent.getSerializableExtra("newTodo", ToDo.class);
        todoList.add(newToDo);
        binding.recTodo.getAdapter().notifyItemInserted(todoList.size()-1);
    }*/

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
        binding.recTodo.setVisibility(View.VISIBLE);
        binding.zeroTodo.setVisibility(View.INVISIBLE);
        binding.prgBar.setVisibility(View.VISIBLE);
        repo.getAllToDos(((ToDoApplication)getApplication()).srv,dataHandler);

        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayout.setRefreshing(false);
                repo.getAllToDos(((ToDoApplication)getApplication()).srv,dataHandler);

            }
        });
        /*
        dataHolder.observe(this, toDo -> {
            repo.changeChecked(((ToDoApplication)getApplication()).srv, toDo.getId());
        });*/

    }

    @Override
    public void onButtonClicked(String id) {
        repo.changeChecked(((ToDoApplication)getApplication()).srv, id);
    }


    /*
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the answers when coming back from posting an answer

        repo.getAllToDos(((ToDoApplication)getApplication()).srv,dataHandler);


    }
    */
}