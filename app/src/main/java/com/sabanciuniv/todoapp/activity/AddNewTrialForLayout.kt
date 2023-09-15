package com.sabanciuniv.todoapp.activity

import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import com.sabanciuniv.todoapp.R
import com.sabanciuniv.todoapp.databinding.ActivityAddNewTrialForLayoutBinding
import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.SimpleTimeZone

class AddNewTrialForLayout : AppCompatActivity() {
    /*val isSystem24Hour = is24HourFormat(this)
    val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H*/
    val timePicker =
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(10)
            .setTitleText("Select time")
            .setInputMode(INPUT_MODE_KEYBOARD)
            .build()
    private val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .build()
    var selectedDate: LocalDate? = null
    var selectedTime: LocalTime? = null
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_trial_for_layout)
        val binding = ActivityAddNewTrialForLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Add a New To-Do"
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
            else if(binding.titleText.length() > 32 || binding.descriptionText.length() > 64){
                Snackbar.make(it, "Please match the character bound of the fields.", Snackbar.LENGTH_LONG).show()
            }
            else if( binding.titleText.length()== 0 || binding.descriptionText.length() == 0 ){
                Snackbar.make(it, "Please fill all fields.", Snackbar.LENGTH_LONG).show()
            }
            else{
                val selectedDateTime: LocalDateTime = LocalDateTime.of(selectedDate, selectedTime)
            }
        }
    }
}