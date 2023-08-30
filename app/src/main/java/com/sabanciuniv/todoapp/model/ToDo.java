package com.sabanciuniv.todoapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ToDo implements Serializable {
    private String toDo, title;
    private String dueDate, id;
    private boolean isChecked, isLoading;

    public ToDo(String toDo, String title, String dueDate, boolean isChecked, String id) {
        this.toDo = toDo;
        this.title = title;
        this.dueDate = dueDate;
        this.isChecked = isChecked;
        this.id = id;
        this.isLoading = false;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
