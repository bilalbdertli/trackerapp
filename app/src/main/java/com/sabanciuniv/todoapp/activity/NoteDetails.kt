package com.sabanciuniv.todoapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.sabanciuniv.todoapp.R
import com.sabanciuniv.todoapp.ToDoApplication
import com.sabanciuniv.todoapp.databinding.ActivityNoteDetailsBinding
import com.sabanciuniv.todoapp.model.Note
import com.sabanciuniv.todoapp.repository.ToDoRepository
import com.sabanciuniv.todoapp.viewmodel.NoteViewModel
import java.time.format.DateTimeFormatter

class NoteDetails : AppCompatActivity() {
    private var binding: ActivityNoteDetailsBinding? = null
    private val viewModel: NoteViewModel by viewModels()
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val current = intent.getSerializableExtra("noteDetails") as Note?
        supportActionBar!!.title = "Details of Note"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (current != null) {
            binding!!.seeDetailsTitle.text = current.title
            binding!!.seeDetailsDesc.text = current.note
            binding!!.seeDetailsDueDate.text = current.dueDate?.format(dateFormatter)
        }

        viewModel.responseDeletion.observe(this) { responseDeletion ->
            finish()
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
            val current = intent.getSerializableExtra("noteDetails") as Note?
            current!!.id?.let { viewModel.deleteNote(it) }
        }
        return true
    }
}