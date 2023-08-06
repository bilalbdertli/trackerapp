package com.sabanciuniv.todoapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ToDo implements Serializable {
    public String toDo, title;
    public LocalDateTime dueDate;
    public boolean isChecked;

    public ToDo(String toDo, String title, LocalDateTime dueDate) {
        this.toDo = toDo;
        this.title = title;
        this.dueDate = dueDate;
        this.isChecked = false;
    }

    public ToDo() {}

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
