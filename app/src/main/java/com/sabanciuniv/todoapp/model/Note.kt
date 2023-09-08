package com.sabanciuniv.todoapp.model

import java.io.Serializable

class Note: Serializable {
    var note: String? = null
    var title: String? = null
    var dueDate: String? = null
    var id: String? = null

    constructor(note: String?, title: String?, dueDate: String?,  id: String?) {
        this.note = note
        this.title = title
        this.dueDate = dueDate
        this.id = id
    }

    constructor()
}