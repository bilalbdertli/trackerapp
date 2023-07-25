package com.sabanciuniv.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
    List<ToDo> todos =new ArrayList<>();
    TextView zeroTodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recView =findViewById(R.id.recTodo);
        zeroTodos = findViewById(R.id.zeroTodo);
        getSupportActionBar().setTitle("To-Do's of the Day");


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
            Toast.makeText(this, "asdasdasdasd", Toast.LENGTH_SHORT).show();

        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        recView.setVisibility(View.VISIBLE);
        zeroTodos.setVisibility(View.INVISIBLE);

    }
}