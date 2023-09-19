package com.sabanciuniv.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

class NoteViewModel: ViewModel() {
    var job: Job? = null
    var responseDeletion: String? = null
    fun deleteNote(id: String){
        job = 
    }



    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}