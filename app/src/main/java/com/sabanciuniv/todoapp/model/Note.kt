package com.sabanciuniv.todoapp.model

import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Note: Serializable {
    @SerializedName("note")
    var note: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("dueDate")
    var dueDate: LocalDateTime? = null
    @SerializedName("id")
    var id: String? = null

    constructor(note: String?, title: String?, dueDate: LocalDateTime?,  id: String?) {
        this.note = note
        this.title = title
        this.dueDate = dueDate
        this.id = id
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

        fun fromBackendJson(currentToDo: JSONObject): Note {
            return Note(
                currentToDo.getString("description"),
                currentToDo.getString("title"),
                LocalDateTime.parse(currentToDo.getString("dueDate"), dateFormatter),
                currentToDo.getString("id")
            )
        }
    }
}