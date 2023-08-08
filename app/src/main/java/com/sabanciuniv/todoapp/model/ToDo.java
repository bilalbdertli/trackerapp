package com.sabanciuniv.todoapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ToDo implements Serializable {
    public String toDo, title;
    public String dueDate, id;
    public boolean isChecked;

    public ToDo(String toDo, String title, String dueDate, boolean isChecked, String id) {
        this.toDo = toDo;
        this.title = title;
        this.dueDate = dueDate;
        this.isChecked = isChecked;
        this.id = id;
    }

    public ToDo() {}

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String isChecked() {
        return String.valueOf(isChecked);
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

}
