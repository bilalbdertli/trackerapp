package com.sabanciuniv.todoapp.model

import java.io.Serializable

class ToDo : Serializable {
    var toDo: String? = null
    var title: String? = null
    var dueDate: String? = null
    var id: String? = null
        private set
    var isChecked = false
    var isLoading = false

    constructor(toDo: String?, title: String?, dueDate: String?, isChecked: Boolean, id: String?) {
        this.toDo = toDo
        this.title = title
        this.dueDate = dueDate
        this.isChecked = isChecked
        this.id = id
        isLoading = false
    }

    constructor()
}