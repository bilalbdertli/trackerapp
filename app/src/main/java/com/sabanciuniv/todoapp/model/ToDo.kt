package com.sabanciuniv.todoapp.model

import org.json.JSONObject
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ToDo : Serializable {
    var toDo: String? = null
    var title: String? = null
    var dueDate: LocalDateTime? = null
    var id: String? = null
        private set
    var isChecked = false
    var isLoading = false

    constructor(toDo: String?, title: String?, dueDate: LocalDateTime?, isChecked: Boolean, id: String?) {
        this.toDo = toDo
        this.title = title
        this.dueDate = dueDate
        this.isChecked = isChecked
        this.id = id
        isLoading = false
    }

    constructor()
    fun getShortened(): String{

        if(this.title!!.length > 25){
            return this.title!!.substring(0,  25) + "..."
        }
            return this.title!!
    }

    companion object {
        // Define a DateTimeFormatter for parsing the date and time strings
        private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        fun fromBackendJson(currentToDo: JSONObject): ToDo {
            return ToDo(
                currentToDo.getString("description"),
                currentToDo.getString("title"),
                LocalDateTime.parse(currentToDo.getString("dueDate"), dateFormatter),
                currentToDo.getBoolean("checked"),
                currentToDo.getString("id")
            )
        }
    }
}