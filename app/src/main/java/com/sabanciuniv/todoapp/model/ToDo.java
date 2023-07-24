package com.sabanciuniv.todoapp.model;

public class ToDo {
    public String toDo, dueDate;

    public ToDo(String toDo, String dueDate) {
        this.toDo = toDo;
        this.dueDate = dueDate;
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
}
