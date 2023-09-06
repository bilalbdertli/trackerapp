package com.sabanciuniv.todoapp

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sabanciuniv.todoapp.databinding.ActivityTodoDetailsBinding
import com.sabanciuniv.todoapp.model.ToDo
import com.sabanciuniv.todoapp.repository.ToDoRepository

class TodoDetails : AppCompatActivity() {
    private var binding: ActivityTodoDetailsBinding? = null
    var handler = Handler { msg ->
        Log.i("DELETE", msg.obj.toString())
        finish()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoDetailsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val current = intent.getSerializableExtra("todoDetails") as ToDo?
        supportActionBar!!.title = "Details of To-Do"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (current != null) {
            binding!!.seeDetailsTitle.text = current.title
            binding!!.seeDetailsDesc.text = current.toDo
            binding!!.seeDetailsDueDate.text = current.dueDate
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.mnDeleteToDo) {
            val srv = (application as ToDoApplication).srv
            val current = intent.getSerializableExtra("todoDetails") as ToDo?
            val repo = ToDoRepository()
            current!!.id?.let { repo.deleteToDoById(srv, it, handler) }
            finish()
        }
        return true
    }
}