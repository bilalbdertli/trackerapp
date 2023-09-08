package com.sabanciuniv.todoapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import com.sabanciuniv.todoapp.R
import com.sabanciuniv.todoapp.ToDoApplication
import com.sabanciuniv.todoapp.databinding.ActivityAddNewNoteBinding
import com.sabanciuniv.todoapp.repository.ToDoRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddNewNote : AppCompatActivity() {
    private var binding: ActivityAddNewNoteBinding? = null
    var selectedCalenderDate = LocalDateTime.now()
    var handler = Handler {
        finish()
        true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewNoteBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar!!.title = "Add a New Note"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val srv = (application as ToDoApplication).srv
        binding!!.chooseCalendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            selectedCalenderDate =
                LocalDateTime.of(year, month + 1, dayOfMonth, 10, 10, 10)
        }
        binding!!.btnAddTodo.setOnClickListener { v ->
            if (!(binding!!.txtNewDescription.text.toString()
                    .equals("") || binding!!.txtNewTitle.text.toString().equals(""))
            ) {
                binding!!.progressBar.visibility = View.VISIBLE
                binding!!.txtNewDescription.isEnabled = false
                binding!!.txtNewTitle.isEnabled = false
                binding!!.btnAddTodo.isEnabled = false
                val repo = ToDoRepository()
                repo.addNote(
                    srv,
                    handler,
                    binding!!.txtNewTitle.text.toString(),
                    binding!!.txtNewDescription.text.toString(),
                    selectedCalenderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                )
            } else {
                binding!!.txtError.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}