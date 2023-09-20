package com.sabanciuniv.todoapp.viewmodel

import android.util.JsonReader
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sabanciuniv.todoapp.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class NoteViewModel: ViewModel() {
    var responseDeletion = MutableLiveData<String>()
    fun deleteNote(id: String){

        val job: Job = viewModelScope.launch {
            try{
                val response = withContext(Dispatchers.IO){
                    RetrofitInstance.api.deleteNote(id)
                }
                if (response.isSuccessful) {
                    val responseContent = response.body()
                    if(responseContent != null){
                        responseDeletion.postValue(responseContent.toString())
                    }
                    else{
                        responseDeletion.postValue("Error: No response content")
                    }
                } else {
                    responseDeletion.postValue("Error: ${response.code()} ${response.body()}")
                }
            } catch (e: Exception){
                val err = e.toString()
                responseDeletion.postValue(err)
            }
        }
        if (job.isCompleted) {
            job.cancel()
        }
    }
}