package com.sabanciuniv.todoapp.viewmodel

import android.util.JsonReader
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sabanciuniv.todoapp.api.RetrofitInstance
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class NoteViewModel: ViewModel() {
    var job: Job? = null
    var responseDeletion: String? = null
    fun deleteNote(id: String){

        job = viewModelScope.launch {

            responseDeletion = try{
                Log.i("IN-DELETE-FUNCTION", id)

                val response =RetrofitInstance.api.deleteNote(id)
                if (response.isSuccessful) {
                    response.body()?.let { Log.i("succesfully done", it) }
                    val responseContent = response.body()
                    responseContent
                } else {
                    "Error: ${response.code()}"
                }
            } catch (e: Exception){
                val err = e.toString()
                Log.i("INDELETEFUNCTION ERR0R", err)
                err
            }
        }


        if (job!!.isCompleted) {
            Log.i("INVIEWMODEL", "FINISHED")
            job!!.cancel()
        }
    }



    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}