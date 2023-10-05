package com.sabanciuniv.todoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sabanciuniv.todoapp.model.UserCalory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UseCaloryViewModel(initialUserCalory: UserCalory): ViewModel() {
    private var userCaloryData = initialUserCalory
    private val _personLiveData = MutableLiveData<UserCalory>(initialUserCalory)
    val personLiveData: LiveData<UserCalory> = _personLiveData

    fun resetClicked() {
        userCaloryData = UserCalory(0, userCaloryData.goal)
        _personLiveData.value = userCaloryData
    }
    fun foodAdded(cal: Int){
        userCaloryData = UserCalory(userCaloryData.earned + cal, userCaloryData.goal)
        _personLiveData.value = userCaloryData
    }
}