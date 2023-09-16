package com.sabanciuniv.todoapp.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.sabanciuniv.todoapp.R
import com.sabanciuniv.todoapp.ToDoApplication
import com.sabanciuniv.todoapp.databinding.ActivityAddNewNoteBinding
import com.sabanciuniv.todoapp.repository.ToDoRepository
import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class AddNewNote : AppCompatActivity() {
    val timePicker =
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(10)
            .setTitleText("Select time")
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
            .build()
    private val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .build()
    var selectedDate: LocalDate? = null
    var selectedTime: LocalTime? = null
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    var handler = Handler {
        finish()
        true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val srv = (application as ToDoApplication).srv
        supportActionBar!!.title = "Add a New Note"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.dateText.isEnabled = false
        binding.timeText.isEnabled = false

        binding.btnDate.setOnClickListener{
            datePicker.show(supportFragmentManager, "tag");
        }

        datePicker.addOnPositiveButtonClickListener {
            val date: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(it))
            binding.dateText.setText(date)
            selectedDate = LocalDate.parse(date, dateFormatter)
            binding.dateWrapper.hint = "Selected Date"
        }
        binding.btnTime.setOnClickListener{
            timePicker.show(supportFragmentManager, "tag")
        }

        timePicker.addOnPositiveButtonClickListener {
            val time: String = MessageFormat.format("{0}:{1}",
                String.format(Locale.getDefault(), "%02d", timePicker.hour),
                String.format(Locale.getDefault(), "%02d", timePicker.minute))
            binding.timeText.setText(time)
            selectedTime = LocalTime.parse(time, timeFormatter)

        }

        binding.btnSave.setOnClickListener{
            if(selectedTime == null || selectedDate == null){
                Snackbar.make(it, "Please select the date and time.", Snackbar.LENGTH_LONG).show()
            }
            else if(binding.titleText.length() > 32 || binding.descriptionText.length() > 128){
                Snackbar.make(it, "Please match the character bound of the fields.", Snackbar.LENGTH_LONG).show()
            }
            else if( binding.titleText.length()== 0 || binding.descriptionText.length() == 0 ){
                Snackbar.make(it, "Please fill all fields.", Snackbar.LENGTH_LONG).show()
            }
            else{
                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if(this.currentFocus != null){
                    imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
                }
                binding.progressBar.visibility = View.VISIBLE
                val selectedDateTime: LocalDateTime = LocalDateTime.of(selectedDate, selectedTime)
                val repo = ToDoRepository()
                repo.addNote(srv, handler, binding.titleText.text.toString(), binding.descriptionText.text.toString(), selectedDateTime )
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