package com.sabanciuniv.todoapp;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ToDoApplication extends Application {
    public ExecutorService srv =  Executors.newCachedThreadPool();
}
