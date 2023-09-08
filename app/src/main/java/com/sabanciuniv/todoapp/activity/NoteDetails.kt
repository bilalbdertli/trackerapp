package com.sabanciuniv.todoapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.sabanciuniv.todoapp.R
import com.sabanciuniv.todoapp.ToDoApplication
import com.sabanciuniv.todoapp.databinding.ActivityNoteDetailsBinding
import com.sabanciuniv.todoapp.model.Note
import com.sabanciuniv.todoapp.repository.ToDoRepository

class NoteDetails : AppCompatActivity() {
    private var binding: ActivityNoteDetailsBinding? = null
    var handler = Handler { msg ->
        Log.i("DELETE", msg.obj.toString())
        finish()
        true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_note_details)
        val current = intent.getSerializableExtra("noteDetails") as Note?
        supportActionBar!!.title = "Details of Note"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (current != null) {
            binding!!.seeDetailsTitle.text = current.title
            binding!!.seeDetailsDesc.text = current.note
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
            val current = intent.getSerializableExtra("noteDetails") as Note?
            val repo = ToDoRepository()
            current!!.id?.let { repo.deleteNoteById(srv, it, handler) }
            finish()
        }
        return true
    }
}